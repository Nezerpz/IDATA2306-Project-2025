package no.ntnu.rentalroulette.service;

import java.util.List;
import java.util.Objects;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

  @Autowired
  private CarRepository carRepository;

  @Override
  public List<Car> getAllCars() {
    return carRepository.findAll();
  }

  @Override
  public Car getCarById(int id) {
    return carRepository.findById(id).orElse(null);
  }

  @Override
  public Car addCar(Car car) {
    return carRepository.save(car);
  }

  @Override
  public Car updateCar(Car car) {
    Car existingCar = carRepository.findById(car.getId()).get();

    if (Objects.nonNull(car.getCarModel()) && "".equalsIgnoreCase(car.getCarModel())) {
      existingCar.setCarModel(car.getCarModel());
    }
    if (Objects.nonNull(car.getFuelType()) && "".equalsIgnoreCase(car.getFuelType())) {
      existingCar.setFuelType(car.getFuelType());
    }
    if (Objects.nonNull(car.getTransmissionType()) &&
        "".equalsIgnoreCase(car.getTransmissionType())) {
      existingCar.setTransmissionType(car.getTransmissionType());
    }
    if (car.getProductionYear() != 0) {
      existingCar.setProductionYear(car.getProductionYear());
    }
    if (car.getNumberOfSeats() != 0) {
      existingCar.setNumberOfSeats(car.getNumberOfSeats());
    }

    return carRepository.save(existingCar);
  }

  @Override
  public void deleteCar(int id) {
    carRepository.deleteById(id);
  }
}
