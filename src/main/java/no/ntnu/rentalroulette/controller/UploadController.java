package no.ntnu.rentalroulette.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import no.ntnu.rentalroulette.service.StorageService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UploadController {

    private final StorageService storageService;

	@Autowired
	public UploadController(StorageService storageService) {
		this.storageService = storageService;
	}

    @PostMapping("/upload")
	public String handleFileUpload(
            @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes
    ) {

		storageService.store(file);
        String filePath = "/user-upload/" + file.getName();

		return filePath;
	}
}
