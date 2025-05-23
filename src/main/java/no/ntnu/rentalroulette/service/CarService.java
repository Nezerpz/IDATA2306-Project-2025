package no.ntnu.rentalroulette.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.entity.User;
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
import no.ntnu.rentalroulette.util.ManufacturerUtil;
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
        carRepository.findAll().stream()
            .filter(car -> orderRepository.findAllByCarId(car.getId()).stream()
                .noneMatch(order -> order.getDateTo().isAfter(startDate) ||
                    (order.getDateTo().isEqual(startDate) &&
                        order.getTimeTo().isAfter(startTime))))
            .filter(car -> car.getCarStatus() != CarStatus.UNAVAILABLE).toList()
    );
  }


  /**
   * Extracts a list of features from the request body.
   *
   * @param features The request body.
   * @return The list of features.
   */
  public List<Feature> getFeaturesFromRequestBody(JsonNode features) {
    List<Feature> featureList = new ArrayList<>();
    if (features.isArray()) {
      for (JsonNode featureNode : features) {
        int featureId = Integer.parseInt(featureNode.asText());
        featureList.add(featureRepository.findById(featureId).get());
      }
    }

    return featureList;
  }

  /**
   * Get the three highest rated cars.
   *
   * @return a list of the three highest rated cars
   */
  public List<ObjectNode> getTopRatedAvailableCars() {
    return getCarsWithAverageRating(
        carRepository.findAll().stream()
            .filter(car -> orderRepository.findAllByCarId(car.getId()).stream()
                .noneMatch(order -> order.getDateTo().isAfter(LocalDate.now()) ||
                    (order.getDateTo().isEqual(LocalDate.now()) &&
                        order.getTimeTo().isAfter(LocalTime.now()))))
            .sorted((car1, car2) -> {
              List<CarReview> reviews1 = carReviewRepository.findAllByCar(car1);
              List<CarReview> reviews2 = carReviewRepository.findAllByCar(car2);

              double avgRating1 = reviews1.isEmpty() ? 0.0 :
                  reviews1.stream().mapToInt(CarReview::getRating).average().orElse(0.0);
              double avgRating2 = reviews2.isEmpty() ? 0.0 :
                  reviews2.stream().mapToInt(CarReview::getRating).average().orElse(0.0);

              return Double.compare(avgRating2, avgRating1);
            })
            .filter(car -> {
              List<CarReview> reviews = carReviewRepository.findAllByCar(car);
              double avgRating = reviews.isEmpty() ? 0.0 :
                  reviews.stream().mapToInt(CarReview::getRating).average().orElse(0.0);
              return avgRating > 0;
            })
            .limit(3)
            .toList()
    );
  }


  @Transactional
  public void addCar(ObjectNode requestBody, User user) {
    String imagePath = requestBody.has("imagePath")
        ? requestBody.get("imagePath").asText()
        : "";
    String model = requestBody.get("carModel").asText();
    String manufacturer = requestBody.get("manufacturer").asText();
    int seats = requestBody.get("numberOfSeats").asInt();
    TransmissionType transmissionType =
        TransmissionType.valueOf(requestBody.get("transmissionType").asText());
    FuelType fuelType = FuelType.valueOf(requestBody.get("fuelType").asText());
    int price = requestBody.get("price").asInt();
    int productionYear = requestBody.get("productionYear").asInt();
    List<Feature> features = getFeaturesFromRequestBody(requestBody.get("features"));
    Car car = new Car();
    carSetOperations(imagePath, model, manufacturer, seats, transmissionType, fuelType, price,
        productionYear, car);
    car.setFeatures(features);
    car.setUser(user);
    car.setCarStatus(CarStatus.AVAILABLE);
    carRepository.save(car);
  }

  private void carSetOperations(String imagePath, String model, String manufacturer, int seats,
                                TransmissionType transmissionType, FuelType fuelType, int price,
                                int productionYear, Car car) {
    car.setImagePath(imagePath);
    car.setCarModel(model);
    car.setManufacturer(manufacturer);
    car.setNumberOfSeats(seats);
    car.setTransmissionType(transmissionType);
    car.setFuelType(fuelType);
    car.setPrice(price);
    car.setProductionYear(productionYear);
  }

  @Transactional
  public void updateCar(ObjectNode requestBody, int carId) {
    System.out.println(requestBody);
    String imagePath = requestBody.get("imagePath").asText();
    String model = requestBody.get("carModel").asText();
    String manufacturer = requestBody.get("manufacturer").asText();
    int seats = requestBody.get("numberOfSeats").asInt();
    TransmissionType transmissionType =
        TransmissionType.valueOf(requestBody.get("transmissionType").asText());
    FuelType fuelType = FuelType.valueOf(requestBody.get("fuelType").asText());
    int price = requestBody.get("price").asInt();
    int productionYear = requestBody.get("productionYear").asInt();
    CarStatus carStatus = CarStatus.valueOf(requestBody.get("carStatus").asText());
    List<Feature> features = getFeaturesFromRequestBody(requestBody.get("features"));
    Car car = carRepository.findById(carId);
    carSetOperations(imagePath, model, manufacturer, seats, transmissionType, fuelType, price,
        productionYear, car);
    car.setCarStatus(carStatus);
    car.getFeatures().clear();
    car.setFeatures(features);
    carRepository.save(car);
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
