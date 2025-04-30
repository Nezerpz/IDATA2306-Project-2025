package no.ntnu.rentalroulette.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import java.util.List;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.enums.UserType;
import no.ntnu.rentalroulette.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User getUserById(int id) {
    return userRepository.findById(id).orElse(null);
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
    userRepository.deleteById(id);
  }

  @Transactional
  public void updateUserType(int id, UserType userType) {
    User user = userRepository.findById(id).orElse(null);
    if (user != null) {
      user.setUserType(userType);
      userRepository.save(user);
    }
  }
}
