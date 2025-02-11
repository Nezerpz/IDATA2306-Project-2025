package no.ntnu.rentalroulette;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.entity.CarManufacturer;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.entity.UserType;
import no.ntnu.rentalroulette.repository.CarManufacturerRepository;
import no.ntnu.rentalroulette.repository.CarRepository;
import no.ntnu.rentalroulette.repository.UserRepository;

import org.springframework.context.ApplicationContext;

public class SampleDataGenerator {
  private ApplicationContext context;

  public SampleDataGenerator(ApplicationContext context) {
    this.context = context;
  }

  public void createDefaultEntries() {

    // Manufacturers
    CarManufacturer volkswagen = new CarManufacturer("Volkswagen");
    CarManufacturer tesla = new CarManufacturer("Tesla");
    CarManufacturer nissan = new CarManufacturer("Nissan");
    CarManufacturer bmw = new CarManufacturer("BMW");
    CarManufacturer peugeot = new CarManufacturer("Peugeot");
    CarManufacturer skoda = new CarManufacturer("Skoda");
    CarManufacturer mazda = new CarManufacturer("Mazda");
    List<CarManufacturer> carManufacturers = new ArrayList<>();
    carManufacturers.add(volkswagen);
    carManufacturers.add(tesla);
    carManufacturers.add(nissan);
    carManufacturers.add(bmw);
    carManufacturers.add(peugeot);
    carManufacturers.add(skoda);
    carManufacturers.add(mazda);
    CarManufacturerRepository carManufacturerRepository =
        this.context.getBean(CarManufacturerRepository.class);
    carManufacturerRepository.saveAll(carManufacturers);

    // Cars
    List<Car> cars = new ArrayList<>();
    cars.add(new Car("Golf", volkswagen, 5, "Manual", "Petrol", 2007));
    cars.add(new Car("Model 3", tesla, 5, "Automatic", "Electric", 2019));
    cars.add(new Car("Model Y", tesla, 5, "Automatic", "Electric", 2022));
    cars.add(new Car("Leaf", nissan, 5, "Automatic", "Electric", 2016));
    cars.add(new Car("2", mazda, 5, "Automatic", "Petrol", 2017));
    cars.add(new Car("Transporter", volkswagen, 8, "Manual", "Petrol", 1978));
    cars.add(new Car("M3 Evo", bmw, 4, "Manual", "Petrol", 1988));
    cars.add(new Car("Fabia", skoda, 5, "Automatic", "Diesel", 2011));
    cars.add(new Car("307 SW", peugeot, 7, "Manual", "Diesel", 2008));
    cars.add(new Car("207", peugeot, 5, "Manual", "Diesel", 2007));
    cars.add(new Car("3008", peugeot, 5, "Manual", "Diesel", 2010));
    cars.add(new Car("iOn", peugeot, 4, "Automatic", "Electric", 2015));
    CarRepository carRepository = this.context.getBean(CarRepository.class);
    carRepository.saveAll(cars);

    // Users
    /*
    List<User> users = new ArrayList<>();
    users.add(new User(new UserType("Customer"), "Ola", "Nordmann", "ola.nordmann", "teletubbies",
        "ola.nordmann@telenor.no"));
    UserRepository userRepository = this.context.getBean(UserRepository.class);
    userRepository.saveAll(users);
    */
  }

  public List<UserType> createDefaultUserTypes() {
    List<UserType> userTypes = new ArrayList<>();
    userTypes.add(new UserType("Customer"));
    //TODO: Find out if -> userTypes.add(new UserType("Provider"));
    userTypes.add(new UserType("Admin"));
    return userTypes;
  }


}
