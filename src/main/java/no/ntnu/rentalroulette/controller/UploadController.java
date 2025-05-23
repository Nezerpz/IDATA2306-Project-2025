package no.ntnu.rentalroulette.controller;

import no.ntnu.rentalroulette.exception.StorageException;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import no.ntnu.rentalroulette.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.nio.file.Path;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api")
public class UploadController {

  private final StorageService storageService;

  @Autowired
  public UploadController(StorageService storageService) {
    this.storageService = storageService;
  }

  @PostMapping("/upload")
  @Operation(
      summary = "Upload car image",
      description = "Endpoint used by providers and admins to update car image"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Everything good. Image uploaded to server"
      ),
      @ApiResponse(
          responseCode = "422",
          description = "Invalid image upload (not a image etc.)"
      )
  })
  @PreAuthorize("hasRole('PROVIDER')")
  public ResponseEntity<String> handleFileUpload(
      @RequestParam("file") MultipartFile file
  ) {
    try {
      Path filePath = storageService.store(file);
      return new ResponseEntity<String>("/user-uploads/" + filePath.getFileName().toString(),
          HttpStatus.OK);
    } catch (StorageException e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }
}
