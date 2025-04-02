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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends ControllerUtil {

  @Autowired
  public UserController(JwtUtil jwtUtil, UserRepository userRepository) {
    super(jwtUtil, userRepository);
  }

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/users")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<User>> getUsers() {
    List<User> cars = new CopyOnWriteArrayList<User>(userRepository.findAll());
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @GetMapping("/user/{username}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<User> getUser(@PathVariable(value = "username") String username) {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      return new ResponseEntity<>(user.get(), HttpStatus.OK);
    } else {
      throw new UsernameNotFoundException("Username: " + username + " not found");
    }
  }

  @GetMapping("/userType")
  public ResponseEntity<UserType> getUserType(HttpServletRequest request) {
    return new ResponseEntity<>(getUserBasedOnJWT(request).getUserType(), HttpStatus.OK);
  }

  @PostMapping("/become-provider")
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<User> becomeProvider(HttpServletRequest request) {
    User user = getUserBasedOnJWT(request);
    user.setUserType(UserType.PROVIDER);
    userRepository.save(user);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }
}
