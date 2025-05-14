package no.ntnu.rentalroulette.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.entity.CarReview;
import no.ntnu.rentalroulette.entity.Feature;
import no.ntnu.rentalroulette.entity.Order;
import no.ntnu.rentalroulette.enums.CarStatus;
import no.ntnu.rentalroulette.enums.FuelType;
import no.ntnu.rentalroulette.enums.Manufacturer;
import no.ntnu.rentalroulette.enums.TransmissionType;
import no.ntnu.rentalroulette.repository.CarRepository;
import no.ntnu.rentalroulette.repository.CarReviewRepository;
import no.ntnu.rentalroulette.repository.FeatureRepository;
import no.ntnu.rentalroulette.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

  @Autowired
  private CarRepository carRepository;

  @Autowired
  private ReviewService reviewService;

  @Autowired
  private FeatureRepository featureRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private CarReviewRepository carReviewRepository;

  /**
   * Get a car by its ID.
   *
   * @param carId the ID of the car to retrieve
   * @return the car with the specified ID
   */
  public Car getCarById(int carId) {
    return carRepository.findById(carId);
  }

  /**
   * Get all cars with their average rating.
   *
   * @return a list of ObjectNode containing car details and average rating
   */
  public List<ObjectNode> getAllCars() {
    return getCarsWithAverageRating(carRepository.findAll());
  }

  /**
   * Get all cars with their average rating.
   *
   * @param cars the list of cars to get the average rating for
   * @return a list of ObjectNode containing car details and average rating
   */
  private List<ObjectNode> getCarsWithAverageRating(List<Car> cars) {
    return cars.stream().map(car -> {
      List<CarReview> reviews = carReviewRepository.findAllByCar(car);
      double averageRating = reviews.isEmpty() ? 0.0 :
          reviews.stream().mapToInt(CarReview::getRating).average().orElse(0.0);

      ObjectNode carDetails = new ObjectMapper().convertValue(car, ObjectNode.class);
      carDetails.put("averageRating", averageRating);

      return carDetails;
    }).toList();
  }

  public List<ObjectNode> getAllCarsByProviderId(int providerId) {
    return getCarsWithAverageRating(carRepository.findAllByProviderId(providerId));
  }

  public List<ObjectNode> getAllCarsByDate(LocalDate startDate, LocalDate endDate,
                                           LocalTime startTime,
                                           LocalTime endTime) {

    return getCarsWithAverageRating(
        carRepository.findAvailableCars(startDate, endDate, startTime, endTime).stream().filter(
            car -> car.getCarStatus() != CarStatus.UNAVAILABLE
        ).toList());
  }

  @Transactional
  public void addCar(ObjectNode requestBody, List<Feature> features) {
    String imagePath = requestBody.get("imagePath").asText();
    String model = requestBody.get("carModel").asText();
    Manufacturer manufacturer = Manufacturer.valueOf(requestBody.get("manufacturer").asText());
    int seats = requestBody.get("numberOfSeats").asInt();
    TransmissionType transmissionType =
        TransmissionType.valueOf(requestBody.get("transmissionType").asText());
    FuelType fuelType = FuelType.valueOf(requestBody.get("fuelType").asText());
    int price = requestBody.get("price").asInt();
    int productionYear = requestBody.get("productionYear").asInt();
    Car car = new Car(imagePath, model, manufacturer, seats, transmissionType,
        fuelType, price, productionYear, features);
    carRepository.save(car);
  }

  @Transactional
  public void updateCar(ObjectNode requestBody, int carId, List<Feature> features) {
    String imagePath = requestBody.get("imagePath").asText();
    String model = requestBody.get("carModel").asText();
    Manufacturer manufacturer = Manufacturer.valueOf(requestBody.get("manufacturer").asText());
    int seats = requestBody.get("numberOfSeats").asInt();
    TransmissionType transmissionType =
        TransmissionType.valueOf(requestBody.get("transmissionType").asText());
    FuelType fuelType = FuelType.valueOf(requestBody.get("fuelType").asText());
    int price = requestBody.get("price").asInt();
    int productionYear = requestBody.get("productionYear").asInt();
    CarStatus carStatus = CarStatus.valueOf(requestBody.get("carStatus").asText());
    Car car = carRepository.findById(carId);
    car.setImagePath(imagePath);
    car.setCarModel(model);
    car.setManufacturer(manufacturer);
    car.setNumberOfSeats(seats);
    car.setTransmissionType(transmissionType);
    car.setFuelType(fuelType);
    car.setPrice(price);
    car.setProductionYear(productionYear);
    car.setCarStatus(carStatus);
    car.getFeatures().clear();
    if (features != null) {
      for (Feature feature : features) {
        Feature existingFeature = featureRepository.findByFeatureName(feature.getFeatureName());
        if (existingFeature == null) {
          featureRepository.save(feature);
          car.getFeatures().add(feature);
        } else {
          car.getFeatures().add(existingFeature);
        }
      }
      carRepository.save(car);
    }
  }

  //TODO: Delete the fact that the car is connected to features


  @Transactional
  public void deleteCar(int carId) {
    Car car = carRepository.findById(carId);

    List<Order> orders = orderRepository.findAllByCarId(car.getId());
    for (Order order : orders) {
      order.setCar(null);
    }
    orderRepository.saveAll(orders);

    for (Feature feature : car.getFeatures()) {
      feature.getCars().remove(car);
    }
    featureRepository.saveAll(car.getFeatures());

    reviewService.deleteCarReviewsByCarId(car.getId());

    carRepository.save(car);

    carRepository.deleteById(car.getId());
  }
}
