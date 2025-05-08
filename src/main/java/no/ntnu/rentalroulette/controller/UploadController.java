package no.ntnu.rentalroulette.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import no.ntnu.rentalroulette.service.StorageService;

@Controller
public class UploadController {

    @Autowire StorageService storageService;

    @PostMapping("/upload")
	public String handleFileUpload(
            @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes
    ) {

		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}
}
