package no.ntnu.rentalroulette.controller;

import jakarta.servlet.http.HttpServletRequest;
import no.ntnu.rentalroulette.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
