package no.ntnu.rentalroulette.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.entity.Order;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.enums.UserType;
import no.ntnu.rentalroulette.exception.UserNotFoundException;
import no.ntnu.rentalroulette.repository.OrderRepository;
import no.ntnu.rentalroulette.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ReviewService reviewService;

  @Autowired
  private CarService carService;

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User getUserById(int id) {
    return userRepository.findById(id).orElse(null);
  }

  public String getUsernameById(int id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      String fullName = user.get().getFirstName() + " " + user.get().getLastName();
      return "{\"name\": \"" + fullName + "\"}";
    }
    return null;
  }

  public User getOwnerOfCar(int carId) {
    Car car = carService.getCarById(carId);
    if (car != null) {
      return car.getUser();
    }
    return null;

  }

  @Transactional
  public User createUser(ObjectNode requestBody) {
    User newUser = new User(
        UserType.CUSTOMER,
        requestBody.get("firstName").asText(),
        requestBody.get("lastName").asText(),
        requestBody.get("username").asText(),
        requestBody.get("password").asText(),
        requestBody.get("email").asText(),
        requestBody.get("phone").asText()
    );
    newUser.setPassword(encoder.encode(newUser.getPassword()));
    userRepository.save(newUser);
    return newUser;
  }

  @Transactional
  public void deleteUserById(int id) {
    User user = userRepository.findById(id).orElse(null);
    if (user != null) {
      List<Order> customerOrders = orderRepository.findAllByCustomerId(id);
      List<Order> providerOrders = orderRepository.findAllByProviderId(id);
      for (Order order : customerOrders) {
        order.setCustomer(null);
      }
      for (Order order : providerOrders) {
        order.setProvider(null);
      }
      for (Car car : user.getCars()) {
        carService.deleteCar(car.getId());
      }
      reviewService.nullifyReviewerByUserId(id);
      System.out.println("Deleting user: " + user.getFirstName() + " " + user.getLastName());
      user.getCars().clear();
      orderRepository.saveAll(customerOrders);
      orderRepository.saveAll(providerOrders);
      userRepository.delete(user);
    }
  }

  @Transactional
  public void updateUserType(int id, UserType userType) {
    User user = userRepository.findById(id).orElse(null);
    if (user != null) {
      user.setUserType(userType);
      userRepository.save(user);
    }
  }

  @Transactional
  public void suspendUserById(int id) {
    User user = userRepository.findById(id).get();
    user.setActive(false);
    userRepository.save(user);
  }

  @Transactional
  public void unsuspendUserById(int id) {
    User user = userRepository.findById(id).get();
    user.setActive(true);
    userRepository.save(user);
  }

  @Transactional
  public void changeFirstName(int id, String firstName) throws UserNotFoundException {
    User user = getUserById(id);

    if (user == null) {
        throw new UserNotFoundException("User not found");
    }

    if (firstName != null && !firstName.isEmpty()) { 
        user.setFirstName(firstName);
        userRepository.save(user);
    }
  }

  @Transactional
  public void changeLastName(int id, String lastName) throws UserNotFoundException {
    User user = getUserById(id);

    if (user == null) {
        throw new UserNotFoundException("User not found");
    }

    if (lastName != null && !lastName.isEmpty()) { 
        user.setLastName(lastName);
        userRepository.save(user);
    }
  }

  @Transactional
  public void changeUsername(int id, String username) throws UserNotFoundException {
    User user = getUserById(id);

    if (user == null) {
        throw new UserNotFoundException("User not found");
    }

    if (username != null && !username.isEmpty()) { 
        user.setUsername(username);
        userRepository.save(user);
    }
  }

  @Transactional
  public void changeEmail(int id, String email) throws UserNotFoundException {
    User user = getUserById(id);

    if (user == null) {
        throw new UserNotFoundException("User not found");
    }

    if (email != null && !email.isEmpty()) {
        user.setEmail(email);
        userRepository.save(user);
    }
  }

  @Transactional
  public void changePassword(int id, String password) throws UserNotFoundException {
    User user = getUserById(id);

    if (user == null) {
        throw new UserNotFoundException("User not found");
    }

    if (password != null && !password.isEmpty()) {
      user.setPassword(password);
      userRepository.save(user);
    }
  }

}
