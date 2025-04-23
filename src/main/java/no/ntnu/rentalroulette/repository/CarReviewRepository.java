package no.ntnu.rentalroulette.repository;

import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.entity.CarReview;
import no.ntnu.rentalroulette.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarReviewRepository extends JpaRepository<CarReview, Integer> {
  boolean existsByCarAndUser(Car car, User user);
}
