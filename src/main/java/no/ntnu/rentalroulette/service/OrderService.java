package no.ntnu.rentalroulette.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.entity.Order;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.enums.CarStatus;
import no.ntnu.rentalroulette.enums.OrderStatus;
import no.ntnu.rentalroulette.repository.CarRepository;
import no.ntnu.rentalroulette.repository.OrderRepository;
import no.ntnu.rentalroulette.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private CarRepository carRepository;

  @Autowired
  private UserRepository userRepository;

  public List<Order> getAllOrders() {
    return orderRepository.findAll();
  }

  public Order getOrderById(int id) throws NoSuchFieldException {
    Optional<Order> orderOptional = orderRepository.findById(id);
    if (orderOptional.isPresent()) {
      return orderOptional.get();
    } else {
      throw new NoSuchFieldException("Order with id " + id + " not found");
    }
  }

  public List<Order> getOrdersByCustomerId(int customerId) {
    return orderRepository.findAllByCustomerId(customerId);
  }

  public List<Order> getOrdersByProviderId(int providerId) {
    return orderRepository.findAllByProviderId(providerId);
  }

  private LocalDate stringToDate(String string) {
    String[] dates = string.split("-");
    return LocalDate.of(
        Integer.parseInt(dates[0]),
        Integer.parseInt(dates[1]),
        Integer.parseInt(dates[2])
    );
  }

  private LocalTime stringToTime(String string) {
    String[] hourMinutes = string.split(":");
    return LocalTime.of(
        Integer.parseInt(hourMinutes[0]),
        Integer.parseInt(hourMinutes[1])
    );
  }

  @Transactional
  public void createOrder(User user, ObjectNode requestBody) {
    Car car = carRepository.findById(Integer.parseInt(requestBody.get("id").asText()));

    LocalDate startDate = stringToDate(requestBody.get("dateFrom").asText());
    LocalDate endDate = stringToDate(requestBody.get("dateTo").asText());
    LocalTime startTime = stringToTime(requestBody.get("timeFrom").asText());
    LocalTime endTime = stringToTime(requestBody.get("timeTo").asText());

    validateCarAvailability(car, startDate, endDate, startTime, endTime);

    float totalPrice = car.getPrice() * ChronoUnit.DAYS.between(startDate, endDate);

    Optional<User> provider = userRepository.findById(car.getUser().getId());
    if (provider.isEmpty()) {
      throw new IllegalArgumentException("Provider not found");
    }
    if (car.getUser().getId() == user.getId()) {
      throw new IllegalArgumentException("Cannot rent your own car");
    }

    Order order = new Order(
        user,
        provider.get(),
        startDate,
        endDate,
        startTime,
        endTime,
        "" + totalPrice,
        car,
        OrderStatus.PENDING
    );

    car.setCarStatus(CarStatus.INUSE);
    carRepository.save(car);

    orderRepository.save(order);
  }

  private void validateCarAvailability(Car car, LocalDate startDate, LocalDate endDate,
                                       LocalTime startTime, LocalTime endTime) {

    List<Order> existingOrders = orderRepository.findAllByCarId(car.getId());
    for (Order order : existingOrders) {
      boolean datesOverlap =
          !(endDate.isBefore(order.getDateFrom()) || startDate.isAfter(order.getDateTo()));
      boolean timesOverlap = datesOverlap &&
          !(endTime.isBefore(order.getTimeFrom()) || startTime.isAfter(order.getTimeTo()));

      if (datesOverlap && timesOverlap) {
        throw new IllegalArgumentException("Car is already booked during the requested timeframe");
      }
    }
  }

  @Transactional
  public void updateOrder(ObjectNode requestBody, int id) throws NoSuchFieldException,
      IllegalStateException {
    Order existingOrder = getOrderById(id);

    if (existingOrder.getOrderStatus() == OrderStatus.COMPLETED
        || existingOrder.getOrderStatus() == OrderStatus.CANCELLED) {
      throw new IllegalStateException("Cannot update a completed or cancelled order");
    }


    OrderStatus orderStatus = OrderStatus.valueOf(requestBody.get("orderStatus").asText());

    if (orderStatus == OrderStatus.COMPLETED || orderStatus == OrderStatus.CANCELLED) {
      LocalDate nowDate = LocalDate.now();
      LocalTime nowTime = LocalTime.now(Clock.system(ZoneId.of("Europe/Oslo")));
      if (existingOrder.getDateTo().isAfter(nowDate) ||
          (existingOrder.getDateTo().isEqual(nowDate) &&
              existingOrder.getTimeTo().isAfter(nowTime))) {
        existingOrder.setDateTo(nowDate);
        existingOrder.setTimeTo(nowTime);
      }
    }

    if (existingOrder.getOrderStatus() != OrderStatus.COMPLETED
        && existingOrder.getOrderStatus() != OrderStatus.CANCELLED) {
      existingOrder.setOrderStatus(orderStatus);
      Car car = existingOrder.getCar();
      if (car.getCarStatus() == CarStatus.INUSE) {
        car.setCarStatus(CarStatus.AVAILABLE);
      }
      carRepository.save(car);
    }

    orderRepository.save(existingOrder);
  }
}
