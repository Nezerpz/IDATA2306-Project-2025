package no.ntnu.rentalroulette;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.entity.CarManufacturer;
import no.ntnu.rentalroulette.entity.FuelType;
import no.ntnu.rentalroulette.entity.TransmissionType;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.entity.UserType;
import no.ntnu.rentalroulette.repository.CarManufacturerRepository;
import no.ntnu.rentalroulette.repository.CarRepository;
import no.ntnu.rentalroulette.repository.FuelTypeRepository;
import no.ntnu.rentalroulette.repository.TransmissionTypeRepository;
import no.ntnu.rentalroulette.repository.UserRepository;

import no.ntnu.rentalroulette.repository.UserTypeRepository;
import org.springframework.context.ApplicationContext;

public class SampleDataGenerator {
  private ApplicationContext context;

  public SampleDataGenerator(ApplicationContext context) {
    this.context = context;
  }

  public void createDefaultEntries() {

    // Manufacturers
    CarManufacturerRepository carManufacturerRepository =
        this.context.getBean(CarManufacturerRepository.class);
    List<CarManufacturer> carManufacturers = createDefaultCarManufacturers();
    carManufacturerRepository.saveAll(carManufacturers);


    // Transmission types
    TransmissionTypeRepository transmissionTypeRepository =
        this.context.getBean(TransmissionTypeRepository.class);
    List<TransmissionType> transmissionTypes = createDefaultTransmissionTypes();
    transmissionTypeRepository.saveAll(transmissionTypes);

    // Fuel types
    FuelTypeRepository fuelTypeRepository = this.context.getBean(FuelTypeRepository.class);
    List<FuelType> fuelTypes = createDefaultFuelTypes();
    fuelTypeRepository.saveAll(fuelTypes);

    // Cars
    CarRepository carRepository = this.context.getBean(CarRepository.class);
    List<Car> cars = createDefaultCars(carManufacturerRepository, transmissionTypeRepository,
        fuelTypeRepository);
    carRepository.saveAll(cars);

    // User types
    UserTypeRepository userTypeRepository = this.context.getBean(UserTypeRepository.class);
    List<UserType> userTypes = createDefaultUserTypes();
    userTypeRepository.saveAll(userTypes);

    // Users

    UserRepository userRepository = this.context.getBean(UserRepository.class);
    List<User> users = createDefaultUsers(userTypeRepository);
    userRepository.saveAll(users);
  }

  public List<UserType> createDefaultUserTypes() {
    List<UserType> userTypes = new ArrayList<>();
    userTypes.add(new UserType("Customer"));
    //TODO: Find out if -> userTypes.add(new UserType("Provider"));
    userTypes.add(new UserType("Admin"));
    return userTypes;
  }

  public List<User> createDefaultUsers(UserTypeRepository userTypeRepository) {
    List<User> users = new ArrayList<>();
    users.add(
        new User(userTypeRepository.findByUserType("Customer"), "Ola", "Nordmann", "ola.nordmann",
            "teletubbies",
            "ola.nordmann@telenor.no"));
    users.add(
        new User(userTypeRepository.findByUserType("Customer"), "Kari", "Nordmann", "kari.nordmann",
            "cars",
            "kari.nordmann@telenor.no"));
    users.add(
        new User(userTypeRepository.findByUserType("Admin"), "Jeremy", "Clarkson", "jClarkson",
            "france",
            "jClarkson@rentalroulette.fr"));
    return users;
  }

  public List<CarManufacturer> createDefaultCarManufacturers() {
    List<CarManufacturer> carManufacturers = new ArrayList<>();
    carManufacturers.add(new CarManufacturer("Volkswagen"));
    carManufacturers.add(new CarManufacturer("Tesla"));
    carManufacturers.add(new CarManufacturer("Nissan"));
    carManufacturers.add(new CarManufacturer("BMW"));
    carManufacturers.add(new CarManufacturer("Peugeot"));
    carManufacturers.add(new CarManufacturer("Skoda"));
    carManufacturers.add(new CarManufacturer("Mazda"));
    return carManufacturers;
  }

  public List<TransmissionType> createDefaultTransmissionTypes() {
    List<TransmissionType> transmissionTypes = new ArrayList<>();
    transmissionTypes.add(new TransmissionType("Manual"));
    transmissionTypes.add(new TransmissionType("Automatic"));
    return transmissionTypes;
  }

  public List<FuelType> createDefaultFuelTypes() {
    List<FuelType> fuelTypes = new ArrayList<>();
    fuelTypes.add(new FuelType("Petrol"));
    fuelTypes.add(new FuelType("Diesel"));
    fuelTypes.add(new FuelType("Electric"));
    return fuelTypes;
  }

  public List<Car> createDefaultCars(CarManufacturerRepository carManufacturerRepository,
                                     TransmissionTypeRepository transmissionTypeRepository,
                                     FuelTypeRepository fuelTypeRepository) {
    List<Car> cars = new ArrayList<>();
    CarManufacturer volkswagen = carManufacturerRepository.findByName("Volkswagen");
    CarManufacturer tesla = carManufacturerRepository.findByName("Tesla");
    CarManufacturer nissan = carManufacturerRepository.findByName("Nissan");
    CarManufacturer bmw = carManufacturerRepository.findByName("BMW");
    CarManufacturer peugeot = carManufacturerRepository.findByName("Peugeot");
    CarManufacturer skoda = carManufacturerRepository.findByName("Skoda");
    CarManufacturer mazda = carManufacturerRepository.findByName("Mazda");
    TransmissionType manual = transmissionTypeRepository.findByTransmissionType("Manual");
    TransmissionType automatic = transmissionTypeRepository.findByTransmissionType("Automatic");
    FuelType petrol = fuelTypeRepository.findByFuelType("Petrol");
    FuelType diesel = fuelTypeRepository.findByFuelType("Diesel");
    FuelType electric = fuelTypeRepository.findByFuelType("Electric");
    cars.add(new Car("Golf", volkswagen, 5, manual, petrol, 2007));
    cars.add(new Car("Model 3", tesla, 5, automatic, electric, 2019));
    cars.add(new Car("Model Y", tesla, 5, automatic, electric, 2022));
    cars.add(new Car("Leaf", nissan, 5, automatic, electric, 2016));
    cars.add(new Car("2", mazda, 5, automatic, petrol, 2017));
    cars.add(new Car("Transporter", volkswagen, 8, manual, petrol, 1978));
    cars.add(new Car("M3 Evo", bmw, 4, manual, petrol, 1988));
    cars.add(new Car("Fabia", skoda, 5, automatic, diesel, 2011));
    cars.add(new Car("307 SW", peugeot, 7, manual, diesel, 2008));
    cars.add(new Car("207", peugeot, 5, manual, diesel, 2007));
    cars.add(new Car("3008", peugeot, 5, manual, diesel, 2010));
    cars.add(new Car("iOn", peugeot, 4, automatic, electric, 2015));
    return cars;
  }

}
