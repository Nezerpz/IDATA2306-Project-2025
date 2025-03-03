package no.ntnu.rentalroulette.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.repository.UserRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/users")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<User>> getUsers() {
    List<User> cars = new CopyOnWriteArrayList<User>(userRepository.findAll());
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @GetMapping("/user/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<User> getUser(@PathVariable(value = "id") int id) {
    User user = userRepository.findById(id);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }
}
