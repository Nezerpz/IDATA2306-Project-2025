package no.ntnu.rentalroulette.service;

import java.util.List;
import java.util.Objects;
import no.ntnu.rentalroulette.entity.CarManufacturer;
import no.ntnu.rentalroulette.repository.CarManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarManufacturerServiceImpl implements CarManufacturerService {

  @Autowired
  private CarManufacturerRepository carManufacturerRepository;

  @Override
  public List<CarManufacturer> getAllCarManufacturers() {
    return carManufacturerRepository.findAll();
  }

  @Override
  public CarManufacturer getCarManufacturerById(int id) {
    return carManufacturerRepository.findById(id).orElse(null);
  }

  @Override
  public CarManufacturer addCarManufacturer(CarManufacturer carManufacturer) {
    return carManufacturerRepository.save(carManufacturer);
  }

  @Override
  public CarManufacturer updateCarManufacturer(CarManufacturer carManufacturer) {
    CarManufacturer existingCarManufacturer =
        carManufacturerRepository.findById(carManufacturer.getId()).get();
    if (Objects.nonNull(carManufacturer.getManufacturerName()) &&
        "".equalsIgnoreCase(carManufacturer.getManufacturerName())) {
      existingCarManufacturer.setManufacturerName(carManufacturer.getManufacturerName());
    }
    return carManufacturerRepository.save(existingCarManufacturer);
  }

  @Override
  public void deleteCarManufacturer(int id) {
    carManufacturerRepository.deleteById(id);
  }
}
