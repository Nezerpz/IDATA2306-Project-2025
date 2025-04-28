package no.ntnu.rentalroulette.service;

import jakarta.transaction.Transactional;
import no.ntnu.rentalroulette.repository.CarReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarReviewService {

  @Autowired
  private CarReviewRepository carReviewRepository;

  @Transactional
  public void deleteCarReviewsByCarId(int carId) {
    carReviewRepository.deleteAllByCarId(carId);
  }
}
