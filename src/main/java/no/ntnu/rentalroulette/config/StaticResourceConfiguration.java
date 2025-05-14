package no.ntnu.rentalroulette.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import no.ntnu.rentalroulette.service.FileSystemStorageService;
import java.nio.file.Path;


@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        FileSystemStorageService service = new FileSystemStorageService();
        Path rootLocation = service.getRootLocation();
        registry
            .addResourceHandler("/src/user-uploads/**")
            .addResourceLocations("file:/" + rootLocation.toAbsolutePath().toString());
    }
}
