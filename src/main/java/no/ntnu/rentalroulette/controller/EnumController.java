package no.ntnu.rentalroulette.controller;


import java.util.List;
import java.util.stream.Stream;
import no.ntnu.rentalroulette.enums.CarStatus;
import no.ntnu.rentalroulette.enums.FuelType;
import no.ntnu.rentalroulette.enums.Manufacturer;
import no.ntnu.rentalroulette.enums.OrderStatus;
import no.ntnu.rentalroulette.enums.TransmissionType;
import no.ntnu.rentalroulette.enums.UserType;
import no.ntnu.rentalroulette.util.ManufacturerUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
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
  public ResponseEntity<List<String>> getManufacturers() {
    List<String> formattedManufacturers = Stream.of(Manufacturer.values())
        .map(ManufacturerUtil::formatManufacturer)
        .toList();

    return ResponseEntity.ok(formattedManufacturers);
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
  @Operation(
      summary = "Returns all fuel types",
      description = "Various cars have different fuel systems. This endpoint returns a list of all fuel types. Used in search-filtering among other things."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Fuel types sent in response"
      )
  })
  public ResponseEntity<List<FuelType>> getFuelTypes() {
    return ResponseEntity.ok(List.of(FuelType.values()));
  }

  @GetMapping("/car-status")
  @Operation(
      summary = "Returns all possible car states",
      description = "As cars within the systems can have different states (being used, not being used, available for renting etc.), we use this endpoint to list all possible states."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Possible car states sent in response"
      )
  })
  public ResponseEntity<List<CarStatus>> getCarStatuses() {
    return ResponseEntity.ok(List.of(CarStatus.values()));
  }

  @GetMapping("/user-types")
  @Operation(
      summary = "Returns all user types",
      description = "Different users have different roles in the system. Some are administrators, some are not etc. This endpoint returns all possible types."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. User types sent in response"
      )
  })
  public ResponseEntity<List<UserType>> getUserTypes() {
    return ResponseEntity.ok(List.of(UserType.values()));
  }

  @GetMapping("/order-statuses")
  @Operation(
      summary = "Returns all possible order states",
      description = "Orders pass through various states from ordering to returning a car. This endpoint returns all the possible states."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Possible order states sent in response"
      )
  })
  public ResponseEntity<List<OrderStatus>> getOrderStatuses() {
    return ResponseEntity.ok(List.of(OrderStatus.values()));
  }
}
