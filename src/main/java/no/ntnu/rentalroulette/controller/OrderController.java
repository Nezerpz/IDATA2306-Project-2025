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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import no.ntnu.rentalroulette.entity.User;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.LocalTime;
import no.ntnu.rentalroulette.enums.CarStatus;
import no.ntnu.rentalroulette.enums.UserType;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.repository.CarRepository;

@RestController
public class OrderController {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private CarRepository carRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ControllerUtil controllerUtil;

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
      List<Order> orders = orderRepository.findAll();
      return ResponseEntity.ok(orders);
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
    Optional<Order> order = orderRepository.findById(id);
    if (order.isPresent()) {
      return ResponseEntity.ok(order.get());
    } else {
      return ResponseEntity.notFound().build();
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
        orderRepository.findAllByCustomerId(controllerUtil.getUserBasedOnJWT(request).getId());
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
        orderRepository.findAllByProviderId(controllerUtil.getUserBasedOnJWT(request).getId());
    return ResponseEntity.ok(orders);
  }

  private LocalDate stringToDate(String string) {
    String[] dates = string.split("-");
    LocalDate date = LocalDate.of(
        Integer.parseInt(dates[0]),
        Integer.parseInt(dates[1]),
        Integer.parseInt(dates[2])
    );
    return date;
  }

  private LocalTime stringToTime(String string) {
    String[] hourMinutes = string.split(":");
    LocalTime time = LocalTime.of(
        Integer.parseInt(hourMinutes[0]),
        Integer.parseInt(hourMinutes[1])
    );
    return time;
  }

  @PostMapping("/order")
  public ResponseEntity<String> orderCar(
      HttpServletRequest request
  ) {
    User user = controllerUtil.getUserBasedOnJWT(request);
    ObjectNode body = controllerUtil.getRequestBody(request);
    System.out.println("body = " + body);
    Car car = carRepository.findById(Integer.parseInt(body.get("id").asText()));

    LocalDate startDate = stringToDate(body.get("dateFrom").asText());
    LocalDate endDate = stringToDate(body.get("dateTo").asText());
    LocalTime startTime = stringToTime(body.get("timeFrom").asText());
    LocalTime endTime = stringToTime(body.get("timeTo").asText());

    float totalPrice = car.getPrice() * ChronoUnit.DAYS.between(startDate, endDate);

    int providerID = car.getProviderId();
    Optional<User> provider = userRepository.findById(providerID);
    Order order = new Order(
        user,
        provider.get(),
        startDate,
        endDate,
        startTime,
        endTime,
        "" + totalPrice,
        car,
        true
    );

    orderRepository.save(order);
    System.out.println("order saved");

    car.setCarStatus(CarStatus.INUSE);
    return ResponseEntity.ok("{\"response\": \"available\"}");
  }
}
