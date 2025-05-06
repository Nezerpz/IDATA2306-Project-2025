package no.ntnu.rentalroulette.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import java.util.Optional;
import no.ntnu.rentalroulette.controller.ControllerUtil;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.entity.CarReview;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.entity.UserReview;
import no.ntnu.rentalroulette.repository.CarRepository;
import no.ntnu.rentalroulette.repository.CarReviewRepository;
import no.ntnu.rentalroulette.repository.UserRepository;
import no.ntnu.rentalroulette.repository.UserReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

  @Autowired
  private CarReviewRepository carReviewRepository;

  @Autowired
  private CarRepository carRepository;

  @Autowired
  private UserReviewRepository userReviewRepository;

  @Autowired
  private UserRepository userRepository;

  @Transactional
  public void reviewUser(ObjectNode requestBody) throws NoSuchFieldException {
    Optional<User> reviewedUser =
        userRepository.findById(requestBody.get("reviewed_user_id").asInt());
    Optional<User> reviewingUser =
        userRepository.findById(requestBody.get("reviewing_user_id").asInt());
    if (reviewedUser.isEmpty() || reviewingUser.isEmpty()) {
      throw new NoSuchFieldException("User not found");
    }
    if (reviewedUser.get().getId() == reviewingUser.get().getId()) {
      throw new IllegalArgumentException("You cannot review yourself");
    }
    boolean reviewExists = userReviewRepository.existsByReviewedUserAndReviewingUser(
        reviewedUser.get(), reviewingUser.get()
    );
    if (reviewExists) {
      throw new IllegalStateException("Review already exists");
    }
    UserReview review = new UserReview(
        reviewedUser.get(),
        reviewingUser.get(),
        requestBody.get("rating").asInt(),
        requestBody.get("review").asText()
    );

    userReviewRepository.save(review);
  }

  @Transactional
  public void reviewCar(ObjectNode requestBody) throws NoSuchFieldException {
    Optional<User> user = userRepository.findById(requestBody.get("user_id").asInt());
    Car car = carRepository.findById(requestBody.get("car_id").asInt());
    if (user.isEmpty()) {
      throw new NoSuchFieldException("User not found");
    }
    if (car == null) {
      throw new NoSuchFieldException("Car not found");
    }
    if (user.get().getId() == car.getProviderId()) {
      throw new IllegalArgumentException("You cannot review your own car");
    }
    boolean reviewExists = carReviewRepository.existsByCarAndUser(car, user.get());
    if (reviewExists) {
      throw new IllegalStateException("Review already exists");
    }
    CarReview review = new CarReview(
        user.get(),
        car,
        requestBody.get("rating").asInt(),
        requestBody.get("review").asText()
    );

    carReviewRepository.save(review);
  }

  @Transactional
  public void deleteCarReviewsByCarId(int carId) {
    carReviewRepository.deleteAllByCarId(carId);
  }

  @Transactional
  public void deleteCarReviewsByUserId(int userId) {
    userReviewRepository.deleteAllByReviewedUserId(userId);
    userReviewRepository.deleteAllByReviewingUserId(userId);
  }
}
