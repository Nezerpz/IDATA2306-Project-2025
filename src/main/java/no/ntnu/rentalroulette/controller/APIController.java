package no.ntnu.rentalroulette.controller;

import java.util.List;
import no.ntnu.rentalroulette.entity.Order;
import no.ntnu.rentalroulette.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Simple example controller
 */
@RestController
public class APIController {

  @Autowired
  private OrderRepository orderRepository;

  @GetMapping("/hello")
  @Operation(
      summary = "A simple test endpoint",
      description = "Returns a small text"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "The small text returned in response body"
      )
  })
  public String greeting() {
    return "Sjalabais";
  }

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


}
