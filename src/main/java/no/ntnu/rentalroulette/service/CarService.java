package no.ntnu.rentalroulette.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.entity.Feature;
import no.ntnu.rentalroulette.enums.CarStatus;
import no.ntnu.rentalroulette.enums.FuelType;
import no.ntnu.rentalroulette.enums.Manufacturer;
import no.ntnu.rentalroulette.enums.TransmissionType;
import no.ntnu.rentalroulette.repository.CarRepository;
import no.ntnu.rentalroulette.repository.FeatureRepository;
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

  public Car getCarById(int carId) {
    return carRepository.findById(carId);
  }

  public List<Car> getAllCars() {
    return carRepository.findAll();
  }

  public List<Car> getAllCarsByProviderId(int providerId) {
    return carRepository.findAllByProviderId(providerId);
  }

  public List<Car> getAllCarsByDate(LocalDate startDate, LocalDate endDate, LocalTime startTime,
                                    LocalTime endTime) {

    return carRepository.findAvailableCars(startDate, endDate, startTime, endTime).stream().filter(
        car -> car.getCarStatus() != CarStatus.UNAVAILABLE
    ).toList();
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


  @Transactional
  public void deleteCar(int carId) {
    Car car = carRepository.findById(carId);
    reviewService.deleteCarReviewsByCarId(car.getId());
    carRepository.deleteById(car.getId());
  }

}
