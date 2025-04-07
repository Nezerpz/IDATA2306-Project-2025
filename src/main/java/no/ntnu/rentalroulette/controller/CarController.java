package no.ntnu.rentalroulette.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.entity.Feature;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.enums.CarStatus;
import no.ntnu.rentalroulette.enums.FuelType;
import no.ntnu.rentalroulette.enums.Manufacturer;
import no.ntnu.rentalroulette.enums.TransmissionType;
import no.ntnu.rentalroulette.repository.CarRepository;
import no.ntnu.rentalroulette.repository.UserRepository;
import no.ntnu.rentalroulette.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {


  @Autowired
  private CarRepository carRepository;

  @Autowired
  private ControllerUtil controllerUtil;

  @GetMapping("/cars")
  public ResponseEntity<List<Car>> getCars() {
    List<Car> cars = new CopyOnWriteArrayList<>(carRepository.findAll());
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @GetMapping("/cars/{id}")
  public ResponseEntity<Car> getCar(@PathVariable(value = "id") int id) {
    Car car = carRepository.findById(id);
    return new ResponseEntity<>(car, HttpStatus.OK);
  }

  @PostMapping("/cars")
  public ResponseEntity<List<Car>> getCarsByDates(@RequestBody DateRange dateRange) {
    List<Car> cars = new CopyOnWriteArrayList<>(carRepository.findAvailableCars(
        dateRange.getDateFrom(),
        dateRange.getDateTo(),
        dateRange.getTimeFrom(),
        dateRange.getTimeTo()
    ));
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @GetMapping("/cars/provider")
  public ResponseEntity<List<Car>> getCarsByProvider(HttpServletRequest request) {
    List<Car> cars = new CopyOnWriteArrayList<>(
        carRepository.findAllByProviderId(controllerUtil.getUserBasedOnJWT(request).getId()));
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @PutMapping("/cars/{id}")
  public ResponseEntity<String> updateCar(HttpServletRequest request, @PathVariable int id) {

    ObjectNode requestBody = controllerUtil.getRequestBody(request);
    Car car = carRepository.findById(id);
    User user = controllerUtil.getUserBasedOnJWT(request);
    if (car == null) {
      return new ResponseEntity<>("Car not found", HttpStatus.NOT_FOUND);
    }
    if (car.getUser().getId() != user.getId()) {
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
    String imagePath = requestBody.has("imagePath") ? requestBody.get("imagePath").asText() : null;
    String model = requestBody.has("carModel") ? requestBody.get("carModel").asText() : null;
    Manufacturer manufacturer = requestBody.has("manufacturer") ?
        Manufacturer.valueOf(requestBody.get("manufacturer").asText()) : null;
    int seats = requestBody.has("numberOfSeats") ? requestBody.get("numberOfSeats").asInt() : 0;
    TransmissionType transmissionType = requestBody.has("transmissionType") ?
        TransmissionType.valueOf(requestBody.get("transmissionType").asText()) : null;
    FuelType fuelType =
        requestBody.has("fuelType") ? FuelType.valueOf(requestBody.get("fuelType").asText()) : null;
    int price = requestBody.has("price") ? requestBody.get("price").asInt() : 0;
    int productionYear =
        requestBody.has("productionYear") ? requestBody.get("productionYear").asInt() : 0;
    CarStatus carStatus =
        requestBody.has("carStatus") ? CarStatus.valueOf(requestBody.get("carStatus").asText()) :
            null;
    List<Feature> features = requestBody.has("features") ?
        controllerUtil.getFeaturesFromRequestBody(requestBody.get("features")) : null;
    car.setImagePath(imagePath);
    car.setCarModel(model);
    car.setManufacturer(manufacturer);
    car.setNumberOfSeats(seats);
    car.setTransmissionType(transmissionType);
    car.setFuelType(fuelType);
    car.setPrice(price);
    car.setProductionYear(productionYear);
    car.setCarStatus(carStatus);
    car.setFeatures(features);
    carRepository.save(car);

    return new ResponseEntity<>(HttpStatus.OK);
  }

/*
  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/cars/models")
  public ResponseEntity<List<String>> getCarModels() {
    List<String> cars = new CopyOnWriteArrayList<String>(carRepository.findDistinctCarModel());
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }
 */
}
