package no.ntnu.rentalroulette.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.repository.UserRepository;
import no.ntnu.rentalroulette.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ControllerUtil {

  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;

  /**
   * Constructor for the ControllerUtil class.
   *
   * @param jwtUtil        The JWT utility class.
   * @param userRepository The user repository.
   */
  @Autowired
  public ControllerUtil(JwtUtil jwtUtil, UserRepository userRepository) {
    this.jwtUtil = jwtUtil;
    this.userRepository = userRepository;
  }

  /**
   * Extracts the JWT token from the request object.
   *
   * @param request The request object.
   * @return The JWT.
   */
  public String extractJwtToken(HttpServletRequest request) {
    return request.getHeader("Authorization").substring(7);
  }

  /**
   * Handles the JWT token to find the username registered in the token.
   *
   * @param request The request object.
   * @return The user of the JWT.
   */
  public User getUserBasedOnJWT(HttpServletRequest request) {
    String jwtToken = extractJwtToken(request);
    String username = jwtUtil.extractUsername(jwtToken);
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new UsernameNotFoundException("Username: " + username + " not found");
    }
  }

  /**
   * Extracts the request body from the request object.
   *
   * @param request The request object.
   * @return The request body as a string.
   */
  public ObjectNode getRequestBody(HttpServletRequest request) {
    StringBuilder stringBuilder = new StringBuilder();
    try (BufferedReader reader = request.getReader()) {
      String line;
      while ((line = reader.readLine()) != null) {
        stringBuilder.append(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    String requestBody = stringBuilder.toString();
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonNodes = null;
    try {
      jsonNodes = objectMapper.readValue(requestBody, ObjectNode.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return jsonNodes;
  }
}