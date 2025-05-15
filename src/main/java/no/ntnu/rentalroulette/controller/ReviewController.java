package no.ntnu.rentalroulette.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import no.ntnu.rentalroulette.entity.CarReview;
import no.ntnu.rentalroulette.entity.UserReview;
import no.ntnu.rentalroulette.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @Operation(
      summary = "Review a user",
      description = "Endpoint used to submit a review made by one user of another user."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Cars are sent in response"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "User (that you are reviewing) could not be found"
      ),
      @ApiResponse(
          responseCode = "400",
          description = "You cannot review yourself"
      ),
      @ApiResponse(
          responseCode = "409",
          description = "You have already made the review"
      )
  })
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
  @Operation(
      summary = "Review a Car",
      description = "Endpoint used to submit a review made by a user (customer) that has used a particular car."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Car review submitted"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Car (you are reviewing) could not be found"
      ),
      @ApiResponse(
          responseCode = "400",
          description = "You cannot review your own car"
      ),
      @ApiResponse(
          responseCode = "409",
          description = "You have already made the review"
      )
  })
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
