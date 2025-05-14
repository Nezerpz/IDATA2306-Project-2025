package no.ntnu.rentalroulette.controller;


import java.util.List;
import no.ntnu.rentalroulette.enums.CarStatus;
import no.ntnu.rentalroulette.enums.FuelType;
import no.ntnu.rentalroulette.enums.Manufacturer;
import no.ntnu.rentalroulette.enums.OrderStatus;
import no.ntnu.rentalroulette.enums.TransmissionType;
import no.ntnu.rentalroulette.enums.UserType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnumController {

  @GetMapping("/manufacturers")
  public ResponseEntity<List<Manufacturer>> getManufacturers() {
    return ResponseEntity.ok(List.of(Manufacturer.values()));
  }

  @GetMapping("/transmission-types")
  public ResponseEntity<List<TransmissionType>> getTransmissionTypes() {
    return ResponseEntity.ok(List.of(TransmissionType.values()));
  }

  @GetMapping("/fuel-types")
  public ResponseEntity<List<FuelType>> getFuelTypes() {
    return ResponseEntity.ok(List.of(FuelType.values()));
  }

  @GetMapping("/car-status")
  public ResponseEntity<List<CarStatus>> getCarStatuses() {
    return ResponseEntity.ok(List.of(CarStatus.values()));
  }

  @GetMapping("/user-types")
  public ResponseEntity<List<UserType>> getUserTypes() {
    return ResponseEntity.ok(List.of(UserType.values()));
  }

  @GetMapping("/order-statuses")
  public ResponseEntity<List<OrderStatus>> getOrderStatuses() {
    return ResponseEntity.ok(List.of(OrderStatus.values()));
  }
}
