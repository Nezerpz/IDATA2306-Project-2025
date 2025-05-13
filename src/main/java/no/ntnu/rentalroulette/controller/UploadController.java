package no.ntnu.rentalroulette.controller;

import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import no.ntnu.rentalroulette.service.StorageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.nio.file.Path;

@Controller
public class UploadController {

    private final StorageService storageService;

	@Autowired
	public UploadController(StorageService storageService) {
		this.storageService = storageService;
	}

    @PostMapping("/upload")
    @PreAuthorize("hasRole('PROVIDER')")
	public ResponseEntity<String> handleFileUpload(
            @RequestParam("file") MultipartFile file
    ) {
		Path filePath = storageService.store(file);
        System.out.println(filePath);
		return new ResponseEntity<String>(filePath.toString(), HttpStatus.OK);
	}
}
