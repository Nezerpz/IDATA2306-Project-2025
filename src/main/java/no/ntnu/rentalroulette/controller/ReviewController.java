package no.ntnu.rentalroulette.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import no.ntnu.rentalroulette.entity.CarReview;
import no.ntnu.rentalroulette.entity.UserReview;
import no.ntnu.rentalroulette.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {

  @Autowired
  private ReviewService reviewService;
  @Autowired
  private ControllerUtil controllerUtil;

  @PostMapping("/review-user")
  public ResponseEntity<String> reviewUser(HttpServletRequest request) {
    try {
      reviewService.reviewUser(controllerUtil.getRequestBody(request));
    } catch (NoSuchFieldException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (IllegalStateException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    return new ResponseEntity<>("User review endpoint", HttpStatus.OK);
  }

  @PostMapping("/review-car")
  public ResponseEntity<String> reviewCar(HttpServletRequest request) {
    try {
      reviewService.reviewCar(controllerUtil.getRequestBody(request));
    } catch (NoSuchFieldException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (IllegalStateException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    return new ResponseEntity<>("Car review endpoint", HttpStatus.OK);
  }


  @GetMapping("/review/user/{userId}")
  public ResponseEntity<List<UserReview>> getUserReviews(@PathVariable int userId) {
    try {
      return new ResponseEntity<>(reviewService.getUserReviews(userId), HttpStatus.OK);
    } catch (NoSuchFieldException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }

  @GetMapping("/review/car/{carId}")
  public ResponseEntity<List<CarReview>> getCarReviews(@PathVariable int carId) {
    try {
      return new ResponseEntity<>(reviewService.getCarReviews(carId), HttpStatus.OK);
    } catch (NoSuchFieldException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
