package no.ntnu.rentalroulette.service;

import jakarta.transaction.Transactional;
import no.ntnu.rentalroulette.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

  @Autowired
  private CarRepository carRepository;

  @Autowired
  private CarReviewService carReviewService;

  @Transactional
  public void deleteCar(int carId) {
    carReviewService.deleteCarReviewsByCarId(carId);
    carRepository.deleteById(carId);
  }
}
