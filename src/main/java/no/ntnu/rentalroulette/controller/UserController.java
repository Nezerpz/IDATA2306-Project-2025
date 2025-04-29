package no.ntnu.rentalroulette.controller;

import java.util.List;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.enums.UserType;
import no.ntnu.rentalroulette.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;


@RestController
public class UserController {

  @Autowired
  private ControllerUtil controllerUtil;

  @Autowired
  private UserService userService;

  @PostMapping("/users")
  public ResponseEntity<List<User>> getUsers(HttpServletRequest request) {
    if (controllerUtil.checkIfAdmin(request)) {
      List<User> users = userService.getAllUsers();
      return new ResponseEntity<>(users, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUser(@PathVariable int id) {
    User user = userService.getUserById(id);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  /* TODO: make signup work
  @PostMapping("/users/new")
  public ResponseEntity<String> createUser(HttpServletRequest request) {
      ObjectNode object = controllerUtil.getRequestBody(request);
      System.out.println(object);
      return new ResponseEntity<>("hei", HttpStatus.OK);
  }
  */

  @GetMapping("/users/self")
  public ResponseEntity<User> getSelf(HttpServletRequest request) {
    User user = controllerUtil.getUserBasedOnJWT(request);
    User userToReturn = userService.getUserById(user.getId());
    return new ResponseEntity<>(userToReturn, HttpStatus.OK);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<String> deleteUser(HttpServletRequest request, @PathVariable int id) {
    if (!controllerUtil.checkIfAdmin(request)) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    userService.deleteUserById(id);
    return new ResponseEntity<>("User deleted", HttpStatus.OK);

  }

  @GetMapping("/userType")
  public ResponseEntity<UserType> getUserType(HttpServletRequest request) {
    return new ResponseEntity<>(controllerUtil.getUserBasedOnJWT(request).getUserType(),
        HttpStatus.OK);
  }

  //TODO: Test this function. Removed a return of user which might be important.
  @PostMapping("/become-provider")
  public ResponseEntity<User> becomeProvider(HttpServletRequest request) {
    if (!controllerUtil.checkIfCustomer(request)) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    userService.updateUserType(
        controllerUtil.getUserBasedOnJWT(request).getId(),
        UserType.PROVIDER
    );
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
