package no.ntnu.rentalroulette.service;

import java.util.List;
import no.ntnu.rentalroulette.entity.CarManufacturer;

public interface CarManufacturerService {
  List<CarManufacturer> getAllCarManufacturers();

  CarManufacturer getCarManufacturerById(int id);

  CarManufacturer addCarManufacturer(CarManufacturer carManufacturer);

  CarManufacturer updateCarManufacturer(CarManufacturer carManufacturer);

  void deleteCarManufacturer(int id);
}
