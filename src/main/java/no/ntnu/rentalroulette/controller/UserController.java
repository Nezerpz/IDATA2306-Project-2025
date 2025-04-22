package no.ntnu.rentalroulette.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.enums.UserType;
import no.ntnu.rentalroulette.repository.UserRepository;

import no.ntnu.rentalroulette.security.JwtUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ControllerUtil controllerUtil;

  @PostMapping("/users")
  public ResponseEntity<List<User>> getUsers(HttpServletRequest request) {
    if (controllerUtil.checkIfAdmin(request)) {
      List<User> users = new CopyOnWriteArrayList<>(userRepository.findAll());
      return new ResponseEntity<>(users, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUser(@PathVariable int id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      return new ResponseEntity<>(user.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("users/self")
  public ResponseEntity<User> getSelf(HttpServletRequest request) {
    User user = controllerUtil.getUserBasedOnJWT(request);
    User userToReturn = userRepository.findById(user.getId()).orElseThrow(
        () -> new UsernameNotFoundException("User not found"));
    return new ResponseEntity<>(userToReturn, HttpStatus.OK);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<String> deleteCar(HttpServletRequest request, @PathVariable int id) {
    if (!controllerUtil.checkIfAdmin(request)) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    Optional<User> userToDelete = userRepository.findById(id);
    if (userToDelete.isPresent()) {
      userRepository.delete(userToDelete.get());
      return new ResponseEntity<>("User deleted", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

  }

  @GetMapping("/userType")
  public ResponseEntity<UserType> getUserType(HttpServletRequest request) {
    return new ResponseEntity<>(controllerUtil.getUserBasedOnJWT(request).getUserType(),
        HttpStatus.OK);
  }

  @PostMapping("/become-provider")
  public ResponseEntity<User> becomeProvider(HttpServletRequest request) {
    if (!controllerUtil.checkIfCustomer(request)) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    User user = controllerUtil.getUserBasedOnJWT(request);
    user.setUserType(UserType.PROVIDER);
    userRepository.save(user);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }
}
