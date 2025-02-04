package no.ntnu.rentalroulette;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class BackendApplication {

    @Autowired
    private Environment env;

	public static void main(String[] args) {

		SpringApplication.run(BackendApplication.class, args);
	}


	@Bean
	public CommandLineRunner run(ApplicationContext context) {
		return args -> {
			SessionUtil su = context.getBean(SessionUtil.class);

			Car car = new Car("Golf", 5, "Manual", "Diesel", 2007);
			CarManufacturer carManufacturer = new CarManufacturer("Volkswagen");
			UserType userType = new UserType("Customer");
			User user = new User(userType, "Ola", "Nordmann", "ola123", "1234", "ola.nordmann@gmail.com");

			su.save(car);
			su.save(carManufacturer);
			su.save(userType);
			su.save(user);
		};
	}


}
