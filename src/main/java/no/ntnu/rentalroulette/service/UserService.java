package no.ntnu.rentalroulette.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.entity.Order;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.enums.UserType;
import no.ntnu.rentalroulette.repository.OrderRepository;
import no.ntnu.rentalroulette.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  public boolean changePassword(ObjectNode requestBody, int id) {
    User user = userRepository.findById(id).orElse(null);
    if (user != null) {
      user.setPassword(requestBody.get("password").asText());
      userRepository.save(user);
      return true;
    }
    return false;
  }
}
