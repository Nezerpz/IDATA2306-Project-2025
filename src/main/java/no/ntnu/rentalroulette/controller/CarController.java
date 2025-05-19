package no.ntnu.rentalroulette.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.service.CarService;
import no.ntnu.rentalroulette.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.ntnu.rentalroulette.util.ControllerUtil;v
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

  @Autowired
  private CarService carService;

  @Autowired
  private ControllerUtil controllerUtil;

  @GetMapping("/cars")
  @Operation(
      summary = "Returns all cars",
      description = "Endpoint used by admins to get registered cars"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Cars are sent in response"
      )
  })
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<ObjectNode>> getCars() {
    List<ObjectNode> cars = carService.getAllCars();
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @GetMapping("/cars/{id}")
  @Operation(
      summary = "Returns one car",
      description = "Endpoint used to get a single car by ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Cars are sent in response"
      )
  })
  public ResponseEntity<Car> getCar(@PathVariable(value = "id") int id) {
    Car car = carService.getCarById(id);
    return new ResponseEntity<>(car, HttpStatus.OK);
  }

  //TODO: Make this a GET-request and fix conflict that arises from doing so
  @PostMapping("/cars")
  @Operation(
      summary = "Returns all cars within timespan",
      description = "Timespan is specified in request body"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Cars are sent in response"
      )
  })
  public ResponseEntity<List<ObjectNode>> getCarsByDates(@RequestBody DateRange dateRange) {
    List<ObjectNode> cars = carService.getAllCarsByDate(
        dateRange.getDateFrom(),
        dateRange.getDateTo(),
        dateRange.getTimeFrom(),
        dateRange.getTimeTo()
    );
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @GetMapping("/cars/provider")
  @Operation(
      summary = "Returns all cars from provider",
      description = "Cars are all from different providers. Here you can see all cars from a particular provider."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Cars are sent in response"
      )
  })
  @PreAuthorize("hasRole('PROVIDER')")
  public ResponseEntity<List<ObjectNode>> getCarsByProvider(HttpServletRequest request) {
    List<ObjectNode> cars =
        carService.getAllCarsByProviderId(controllerUtil.getUserBasedOnJWT(request).getId());
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @PostMapping("/cars/add")
  @Operation(
      summary = "Add a car to database",
      description = "Car providers can add new cars to rent out. This is done with this endpoint."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Car has been added to database"
      )
  })
  @PreAuthorize("hasRole('PROVIDER')")
  public ResponseEntity<String> addCar(HttpServletRequest request) {
    System.out.println(request);
    ObjectNode requestBody = controllerUtil.getRequestBody(request);
    User user = controllerUtil.getUserBasedOnJWT(request);
    carService.addCar(requestBody, user);
    return new ResponseEntity<>(HttpStatus.OK);
  }


  @PutMapping("/cars/{id}")
  @Operation(
      summary = "Update a car in the database",
      description = "Car providers (or admins) can update cars to rent out. This is done with this endpoint."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Car is now updated"
      )
  })
  @PreAuthorize("hasRole('PROVIDER') or hasRole('ADMIN')")
  public ResponseEntity<String> updateCar(HttpServletRequest request, @PathVariable int id) {
    ObjectNode requestBody = controllerUtil.getRequestBody(request);
    carService.updateCar(requestBody, id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/cars/{id}")
  @Operation(
      summary = "Delete a car in the database",
      description = "Car providers (or admins) can delete cars from the database. This is done with this endpoint."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Car is now deleted from database"
      )
  })
  @PreAuthorize("hasRole('PROVIDER') or hasRole('ADMIN')")
  public ResponseEntity<String> deleteCar(@PathVariable int id) {
    carService.deleteCar(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
