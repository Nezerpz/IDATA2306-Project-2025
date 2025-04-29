package no.ntnu.rentalroulette.controller;

import java.lang.Integer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import no.ntnu.rentalroulette.entity.Order;
import no.ntnu.rentalroulette.repository.OrderRepository;
import no.ntnu.rentalroulette.repository.UserRepository;
import no.ntnu.rentalroulette.security.JwtUtil;
import no.ntnu.rentalroulette.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import no.ntnu.rentalroulette.entity.User;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.LocalTime;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.repository.CarRepository;

@RestController
public class OrderController {

  @Autowired
  private ControllerUtil controllerUtil;

  @Autowired
  private OrderService orderService;

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
  public ResponseEntity<List<Order>> orders(HttpServletRequest request) {
    if (controllerUtil.checkIfAdmin(request)) {
      return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

  @GetMapping("/orders/{id}")
  @Operation(
      summary = "Order by ID endpoint",
      description = "Returns a specific order by ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "The order returned in response body"
      )
  })
  public ResponseEntity<Order> getOrderById(@PathVariable int id) {
    try {
      Order order = orderService.getOrderById(id);
      return new ResponseEntity<>(order, HttpStatus.OK);
    } catch (NoSuchFieldException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
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
        orderService.getOrdersByCustomerId(controllerUtil.getUserBasedOnJWT(request).getId());
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
        orderService.getOrdersByProviderId(controllerUtil.getUserBasedOnJWT(request).getId());
    return ResponseEntity.ok(orders);
  }

  @PostMapping("/order")
  public ResponseEntity<String> orderCar(
      HttpServletRequest request
  ) {
    User user = controllerUtil.getUserBasedOnJWT(request);
    ObjectNode requestBody = controllerUtil.getRequestBody(request);

    orderService.createOrder(user, requestBody);

    return ResponseEntity.ok("{\"response\": \"available\"}");
  }
}
