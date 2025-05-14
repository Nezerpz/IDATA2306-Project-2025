package no.ntnu.rentalroulette.repository;

import java.util.List;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.entity.CarReview;
import no.ntnu.rentalroulette.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarReviewRepository extends JpaRepository<CarReview, Integer> {
  boolean existsByCarAndUser(Car car, User user);

  void deleteAllByCarId(int carId);
  
  List<CarReview> findAllByCar(Car car);
  List<CarReview> findAllByUser(User user);
}
