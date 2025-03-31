package no.ntnu.rentalroulette.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import no.ntnu.rentalroulette.entity.Order;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.repository.OrderRepository;
import no.ntnu.rentalroulette.repository.UserRepository;
import no.ntnu.rentalroulette.security.AccessUserService;
import no.ntnu.rentalroulette.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/orders")
  @Operation(
      summary = "Orders endpoint",
      description = "Returns a list of all orders in the system"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "The list of orders returned in response body"
      )
  })
  public ResponseEntity<List<Order>> orders() {
    List<Order> orders = orderRepository.findAll();
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/orders/customer")
  @Operation(
      summary = "Orders by customer endpoint",
      description = "Returns a list of all orders for the authenticated customer"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "The list of orders returned in response body"
      )
  })
  public ResponseEntity<List<Order>> getOrdersByCustomer(HttpServletRequest request) {
    String jwtToken = request.getHeader("Authorization").substring(7);
    String username = jwtUtil.extractUsername(jwtToken);
    Optional<User> user = userRepository.findByUsername(username);
    return user.map(value -> ResponseEntity.ok(orderRepository.findAllByCustomerId(value.getId())))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/orders/provider")
  @Operation(
      summary = "Orders by provider endpoint",
      description = "Returns a list of all orders for a specific provider"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "The list of orders returned in response body"
      )
  })
  public ResponseEntity<List<Order>> getOrdersForProvider(HttpServletRequest request) {
    String jwtToken = request.getHeader("Authorization").substring(7);
    String username = jwtUtil.extractUsername(jwtToken);
    Optional<User> user = userRepository.findByUsername(username);
    return user.map(value -> ResponseEntity.ok(orderRepository.findAllByProviderId(value.getId())))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
