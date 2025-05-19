package no.ntnu.rentalroulette.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.enums.UserType;
import no.ntnu.rentalroulette.service.UserService;
import no.ntnu.rentalroulette.exception.UserNotFoundException;
import no.ntnu.rentalroulette.util.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

  @Autowired
  private ControllerUtil controllerUtil;

  @Autowired
  private UserService userService;

  @PostMapping("/users")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<User>> getUsers() {
    List<User> users = userService.getAllUsers();
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @PutMapping("/users/{id}/update")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> updateUser(
      @PathVariable int id,
      HttpServletRequest request
  ) {

    // Updated values
    ObjectNode requestBody = controllerUtil.getRequestBody(request);
    String newFirstName = requestBody.get("firstName").asText();
    String newLastName = requestBody.get("lastName").asText();
    String newUsername = requestBody.get("username").asText();
    String newEmail = requestBody.get("email").asText();

    try {
      userService.changeFirstName(id, newFirstName);
      userService.changeLastName(id, newLastName);
      userService.changeUsername(id, newUsername);
      userService.changeEmail(id, newEmail);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/users/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<User> getUser(@PathVariable int id) {
    User user = userService.getUserById(id);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @GetMapping("/users/name/{id}")
  public ResponseEntity<String> getUserName(@PathVariable int id) {
    String userName = userService.getUsernameById(id);
    if (userName == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(userName, HttpStatus.OK);
  }

  @PostMapping("/users/new")
  public ResponseEntity<User> createUser(HttpServletRequest request) {
    return new ResponseEntity<>(userService.createUser(controllerUtil.getRequestBody(request)),
        HttpStatus.OK);
  }

  @GetMapping("/users/self")
  public ResponseEntity<User> getSelf(HttpServletRequest request) {
    User user = controllerUtil.getUserBasedOnJWT(request);
    User userToReturn = userService.getUserById(user.getId());
    if (userToReturn == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(userToReturn, HttpStatus.OK);
  }

  @DeleteMapping("/users/self")
  public ResponseEntity<String> deleteSelf(HttpServletRequest request) {
    userService.deleteUserById(controllerUtil.getUserBasedOnJWT(request).getId());
    return new ResponseEntity<>("User deleted", HttpStatus.OK);
  }

  @PutMapping("/users/self/password")
  public ResponseEntity<String> changePasswordSelf(HttpServletRequest request) {
    ObjectNode requestBody = controllerUtil.getRequestBody(request);
    try {
      User user = controllerUtil.getUserBasedOnJWT(request);
      String password = requestBody.get("password").asText();
      userService.changePassword(user.getId(), password);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("/users/{id}/password")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> changePassword(
      @PathVariable int id,
      HttpServletRequest request
  ) {
    ObjectNode requestBody = controllerUtil.getRequestBody(request);
    try {
      User user = userService.getUserById(id);
      System.out.println(requestBody);
      String password = requestBody.get("password").asText();
      userService.changePassword(user.getId(), password);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/users/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> deleteUser(@PathVariable int id) {
    userService.deleteUserById(id);
    return new ResponseEntity<>("User deleted", HttpStatus.OK);

  }

  @GetMapping("/userType")
  public ResponseEntity<UserType> getUserType(HttpServletRequest request) {
    return new ResponseEntity<>(controllerUtil.getUserBasedOnJWT(request).getUserType(),
        HttpStatus.OK);
  }

  @GetMapping("users/owner/{id}")
  public ResponseEntity<User> getOwnerOfCar(@PathVariable int id) {
    User user = userService.getOwnerOfCar(id);
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  //TODO: Test this function. Removed a return of user which might be important.
  @PostMapping("/become-provider")
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<User> becomeProvider(HttpServletRequest request) {
    userService.updateUserType(
        controllerUtil.getUserBasedOnJWT(request).getId(),
        UserType.PROVIDER
    );
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/users/suspend/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> suspendUser(HttpServletRequest request, @PathVariable int id) {
    userService.suspendUserById(id);
    return new ResponseEntity<String>("User suspended", HttpStatus.OK);
  }

  @PostMapping("/users/unsuspend/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> unsuspendUser(HttpServletRequest request, @PathVariable int id) {
    userService.unsuspendUserById(id);
    return new ResponseEntity<String>("User unsuspended", HttpStatus.OK);
  }
}
