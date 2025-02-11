package no.ntnu.rentalroulette.service;

import no.ntnu.rentalroulette.entity.Car;
import java.util.List;

public interface CarService {

  List<Car> getAllCars();

  Car getCarById(int id);

  Car addCar(Car car);

  Car updateCar(Car car);

  void deleteCar(int id);
}
