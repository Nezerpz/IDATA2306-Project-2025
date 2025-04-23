package no.ntnu.rentalroulette.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.entity.CarReview;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.entity.UserReview;
import no.ntnu.rentalroulette.repository.CarRepository;
import no.ntnu.rentalroulette.repository.CarReviewRepository;
import no.ntnu.rentalroulette.repository.UserRepository;
import no.ntnu.rentalroulette.repository.UserReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {

  @Autowired
  private ControllerUtil controllerUtil;

  @Autowired
  private CarRepository carRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CarReviewRepository carReviewRepository;

  @Autowired
  private UserReviewRepository userReviewRepository;

  @PostMapping("/review-user")
  public ResponseEntity<String> reviewUser(HttpServletRequest request) {
    ObjectNode body = controllerUtil.getRequestBody(request);
    Optional<User> reviewedUser = userRepository.findById(body.get("reviewed_user_id").asInt());
    Optional<User> reviewingUser = userRepository.findById(body.get("reviewing_user_id").asInt());
    if (reviewedUser.isEmpty() || reviewingUser.isEmpty()) {
      return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
    if (reviewedUser.get().getId() == reviewingUser.get().getId()) {
      return new ResponseEntity<>("You cannot review yourself", HttpStatus.BAD_REQUEST);
    }
    boolean reviewExists = userReviewRepository.existsByReviewedUserAndReviewingUser(
        reviewedUser.get(), reviewingUser.get()
    );
    if (reviewExists) {
      return new ResponseEntity<>("Review already exists", HttpStatus.CONFLICT);
    }
    UserReview review = new UserReview(
        reviewedUser.get(),
        reviewingUser.get(),
        body.get("rating").asInt(),
        body.get("review").asText()
    );

    userReviewRepository.save(review);

    return new ResponseEntity<>("User review endpoint", HttpStatus.OK);
  }

  @PostMapping("/review-car")
  public ResponseEntity<String> reviewCar(HttpServletRequest request) {
    ObjectNode body = controllerUtil.getRequestBody(request);
    Optional<User> user = userRepository.findById(body.get("user_id").asInt());
    Car car = carRepository.findById(body.get("car_id").asInt());
    if (user.isEmpty()) {
      return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
    if (car == null) {
      return new ResponseEntity<>("Car not found", HttpStatus.NOT_FOUND);
    }
    if (user.get().getId() == car.getProviderId()) {
      return new ResponseEntity<>("You cannot review your own car", HttpStatus.BAD_REQUEST);
    }
    boolean reviewExists = carReviewRepository.existsByCarAndUser(car, user.get());
    if (reviewExists) {
      return new ResponseEntity<>("Review already exists", HttpStatus.CONFLICT);
    }
    CarReview review = new CarReview(
        user.get(),
        car,
        body.get("rating").asInt(),
        body.get("review").asText()
    );
    carReviewRepository.save(review);

    return new ResponseEntity<>("Car review endpoint", HttpStatus.OK);
  }
}
