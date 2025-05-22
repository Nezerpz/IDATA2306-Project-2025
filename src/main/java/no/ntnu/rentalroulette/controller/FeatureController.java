package no.ntnu.rentalroulette.controller;

import java.util.List;
import no.ntnu.rentalroulette.entity.Feature;
import no.ntnu.rentalroulette.service.FeatureService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
public class FeatureController {

  @Autowired
  private FeatureService featureService;

  //TODO: Remove this endpoint, it is only for testing purposes
  @GetMapping("/features")
  @Operation(
      summary = "Get all features",
      description = "Returns a list of all car features in the database"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "The list of features returned in response body"
      )
  })
  public ResponseEntity<List<Feature>> features() {
    return new ResponseEntity<>(featureService.getAllFeatures(), HttpStatus.OK);
  }
}
