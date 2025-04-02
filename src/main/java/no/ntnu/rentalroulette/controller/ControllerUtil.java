package no.ntnu.rentalroulette.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.repository.UserRepository;
import no.ntnu.rentalroulette.security.JwtUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public abstract class ControllerUtil {

  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;

  public ControllerUtil(JwtUtil jwtUtil, UserRepository userRepository) {
    this.jwtUtil = jwtUtil;
    this.userRepository = userRepository;
  }

  /**
   * Handles the JWT token to find the username registered in the token.
   *
   * @param request The request object.
   * @return The user of the JWT token.
   */
  protected User handleJwtAndReturnUser(HttpServletRequest request) {
    String jwtToken = request.getHeader("Authorization").substring(7);
    String username = jwtUtil.extractUsername(jwtToken);
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new UsernameNotFoundException("Username: " + username + " not found");
    }
  }
}