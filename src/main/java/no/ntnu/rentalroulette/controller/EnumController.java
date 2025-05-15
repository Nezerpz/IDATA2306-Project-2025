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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class EnumController {

  @GetMapping("/manufacturers")
  @Operation(
      summary = "Returns all manufacturers",
      description = "All cars are made by a manufacturer. This endpoint returns a list of all manufacturers"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Manufacturers sent in response"
      )
  })
  public ResponseEntity<List<Manufacturer>> getManufacturers() {
    return ResponseEntity.ok(List.of(Manufacturer.values()));
  }

  @GetMapping("/transmission-types")
  @Operation(
      summary = "Returns all transmission types",
      description = "Various cars have different transmission systems. This endpoint returns a list of all of them. Used in search-filtering among other things."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Transmission types sent in response"
      )
  })
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
