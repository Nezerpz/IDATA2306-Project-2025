package no.ntnu.rentalroulette.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import no.ntnu.rentalroulette.entity.Order;
import no.ntnu.rentalroulette.repository.OrderRepository;
import no.ntnu.rentalroulette.repository.UserRepository;
import no.ntnu.rentalroulette.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import no.ntnu.rentalroulette.entity.User;

@RestController
public class OrderController extends ControllerUtil {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  public OrderController(JwtUtil jwtUtil, UserRepository userRepository) {
    super(jwtUtil, userRepository);
  }

  //TODO: Remove this endpoint, it is only for testing purposes
  @GetMapping("/orders")
  @PreAuthorize("hasRole('ADMIN')")
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
    List<Order> orders =
        orderRepository.findAllByCustomerId(getUserBasedOnJWT(request).getId());
    return ResponseEntity.ok(orders);
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
    List<Order> orders =
        orderRepository.findAllByProviderId(getUserBasedOnJWT(request).getId());
    return ResponseEntity.ok(orders);
  }

  @PostMapping("/order")
  public ResponseEntity<String> orderCar(
          @RequestParam(value = "car_id", required = true) 
          String carID, HttpServletRequest request
  ) {
    User user = super.getUserBasedOnJWT(request);
    System.out.println("car_id = " + carID + " user = " + user);
    return ResponseEntity.ok(carID);
  }
}
