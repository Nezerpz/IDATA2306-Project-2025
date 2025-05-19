package no.ntnu.rentalroulette.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import no.ntnu.rentalroulette.entity.Order;
import no.ntnu.rentalroulette.service.OrderService;
import no.ntnu.rentalroulette.util.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import no.ntnu.rentalroulette.entity.User;

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
      summary = "Orders",
      description = "Returns a list of all orders in the system"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "The list of orders returned in response body"
      )
  })
  public ResponseEntity<List<Order>> orders() {
    return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
  }

  @GetMapping("/orders/{id}")
  @Operation(
      summary = "Order by ID",
      description = "Returns a specific order by ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "The order returned in response body"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Order not found"
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Bad request"
      )
  })
  @PreAuthorize("hasRole('ADMIN') or hasRole('PROVIDER')")
  public ResponseEntity<Order> getOrderById(@PathVariable int id) {
    try {
      Order order = orderService.getOrderById(id);
      return new ResponseEntity<>(order, HttpStatus.OK);
    } catch (NoSuchFieldException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (IllegalStateException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/orders/customer")
  @Operation(
      summary = "Orders by customer",
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
  @PreAuthorize("hasRole('PROVIDER')")
  @Operation(
      summary = "Orders by provider",
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

  @PutMapping("/orders/{id}")
  @Operation(
      summary = "Update order",
      description = "Updates a specific order by ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "The order is updated"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Order not found"
      )
  })
  @PreAuthorize("hasRole('PROVIDER') or hasRole('ADMIN')")
  public ResponseEntity<String> updateOrder(HttpServletRequest request, @PathVariable int id) {
    ObjectNode requestBody = controllerUtil.getRequestBody(request);
    try {
      orderService.updateOrder(requestBody, id);
    } catch (NoSuchFieldException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/order")
  @Operation(
      summary = "Order a car",
      description = "User makes a order here."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "The order is registered"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Car is not available"
      ),
  })
  public ResponseEntity<String> orderCar(
      HttpServletRequest request
  ) {
    User user = controllerUtil.getUserBasedOnJWT(request);
    ObjectNode requestBody = controllerUtil.getRequestBody(request);

    orderService.createOrder(user, requestBody);

    return ResponseEntity.ok("{\"response\": \"available\"}");
  }
}
