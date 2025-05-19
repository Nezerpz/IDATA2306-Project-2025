package no.ntnu.rentalroulette.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.enums.UserType;
import no.ntnu.rentalroulette.service.UserService;
import no.ntnu.rentalroulette.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @Operation(
      summary = "Returns list of all users",
      description = "Endpoint used by admins to get all registered users"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Users sent in response"
      )
  })
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<User>> getUsers() {
    List<User> users = userService.getAllUsers();
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @PutMapping("/users/{id}/update")
  @Operation(
      summary = "Updates a particular user",
      description = "Endpoint used by admins to update userinfo by id"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. User updated"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "User not found"
      )
  })
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
      }

      catch (UserNotFoundException e) {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
  }

  @GetMapping("/users/{id}")
  @Operation(
      summary = "Returns a particular user",
      description = "Endpoint used by admins return userinfo by id"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. User updated"
      )
  })
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<User> getUser(@PathVariable int id) {
    User user = userService.getUserById(id);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @GetMapping("/users/name/{id}")
  @Operation(
      summary = "Returns a particular user's username",
      description = "Endpoint used to return username by id"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Username sent in response"
      )
  })
  public ResponseEntity<String> getUserName(@PathVariable int id) {
    String userName = userService.getUsernameById(id);
    if (userName == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(userName, HttpStatus.OK);
  }

  @PostMapping("/users/new")
  @Operation(
      summary = "Create a new user",
      description = "Endpoint used to make a new user (sign up)"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. New user created"
      )
  })
  public ResponseEntity<User> createUser(HttpServletRequest request) {
    return new ResponseEntity<>(userService.createUser(controllerUtil.getRequestBody(request)),
        HttpStatus.OK);
  }

  @GetMapping("/users/self")
  @Operation(
      summary = "Return personal userinfo",
      description = "Endpoint used to return info about your own user (based on JWT)"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Userinfo sent in response"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "NaturalistMetaphysicsException: Self not found"
      )
  })
  public ResponseEntity<User> getSelf(HttpServletRequest request) {
    User user = controllerUtil.getUserBasedOnJWT(request);
    User userToReturn = userService.getUserById(user.getId());
    if (userToReturn == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(userToReturn, HttpStatus.OK);
  }

  @DeleteMapping("/users/self")
  @Operation(
      summary = "Delete personal account",
      description = "Endpoint used to delete your own account (based on JWT)"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. User deleted"
      )
  })
  public ResponseEntity<String> deleteSelf(HttpServletRequest request) {
    userService.deleteUserById(controllerUtil.getUserBasedOnJWT(request).getId());
    return new ResponseEntity<>("User deleted", HttpStatus.OK);
  }

  @PutMapping("/users/self/password")
  @Operation(
      summary = "Update personal password",
      description = "Endpoint used to update your own password (based on JWT)"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Password updated"
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Bad request. (JWT or something else not right)"
      )
  })
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
  @Operation(
      summary = "Update user password",
      description = "Endpoint used by admins to update a user's password (based on id)"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Password updated"
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Bad request. (id or something else not right)"
      )
  })
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
  @Operation(
      summary = "Update user password",
      description = "Endpoint used by admins to delete a user (based on id)"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. User deleted"
      )
  })
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> deleteUser(@PathVariable int id) {
    userService.deleteUserById(id);
    return new ResponseEntity<>("User deleted", HttpStatus.OK);

  }

  @GetMapping("/userType")
  @Operation(
      summary = "Get personal user type",
      description = "Endpoint used to get your own user type (based on id). This can be things like user, provider, admin."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. User type returned in response"
      )
  })
  public ResponseEntity<UserType> getUserType(HttpServletRequest request) {
    return new ResponseEntity<>(controllerUtil.getUserBasedOnJWT(request).getUserType(),
        HttpStatus.OK);
  }

  @GetMapping("users/owner/{id}")
  @Operation(
      summary = "Get car owner",
      description = "Endpoint used to get a car's owner (based on car id)"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Car owner returned in response."
      )
  })
  public ResponseEntity<User> getOwnerOfCar(@PathVariable int id) {
    User user = userService.getOwnerOfCar(id);
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  //TODO: Test this function. Removed a return of user which might be important.
  @PostMapping("/become-provider")
  @Operation(
      summary = "Make yourself a car provider",
      description = "Endpoint used to modify your own user type so that you can rent out cars (based on personal JWT)"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. You are now a car provider."
      )
  })
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<User> becomeProvider(HttpServletRequest request) {
    userService.updateUserType(
        controllerUtil.getUserBasedOnJWT(request).getId(),
        UserType.PROVIDER
    );
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/users/suspend/{id}")
  @Operation(
      summary = "Suspend a user",
      description = "Endpoint used by admins to suspend a user (based on id). This means they can't log in."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. User suspended."
      )
  })
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> suspendUser(HttpServletRequest request, @PathVariable int id) {
    userService.suspendUserById(id);
    return new ResponseEntity<String>("User suspended", HttpStatus.OK);
  }

  @PostMapping("/users/unsuspend/{id}")
  @Operation(
      summary = "Unsuspend a user",
      description = "Endpoint used by admins to unsuspend a user (based on id). This means they are able to log in."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. User suspended."
      )
  })
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> unsuspendUser(HttpServletRequest request, @PathVariable int id) {
    userService.unsuspendUserById(id);
    return new ResponseEntity<String>("User unsuspended", HttpStatus.OK);
  }
}
