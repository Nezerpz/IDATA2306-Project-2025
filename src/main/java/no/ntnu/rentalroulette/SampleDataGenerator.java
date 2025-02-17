package no.ntnu.rentalroulette;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.entity.CarManufacturer;
import no.ntnu.rentalroulette.entity.CarToProvider;
import no.ntnu.rentalroulette.entity.FuelType;
import no.ntnu.rentalroulette.entity.Provider;
import no.ntnu.rentalroulette.entity.TransmissionType;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.entity.UserType;
import no.ntnu.rentalroulette.repository.CarManufacturerRepository;
import no.ntnu.rentalroulette.repository.CarRepository;
import no.ntnu.rentalroulette.repository.FuelTypeRepository;
import no.ntnu.rentalroulette.repository.ProviderRepository;
import no.ntnu.rentalroulette.repository.TransmissionTypeRepository;
import no.ntnu.rentalroulette.repository.UserRepository;

import no.ntnu.rentalroulette.repository.UserTypeRepository;
import org.springframework.context.ApplicationContext;

public class SampleDataGenerator {
  private ApplicationContext context;
  private static final String ADDRESS = "Smibakken 1, Verftsgata 2, 6018 Ålesund";

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

  private List<UserType> createDefaultUserTypes() {
    List<UserType> userTypes = new ArrayList<>();
    userTypes.add(new UserType("Customer"));
    //TODO: Find out if -> userTypes.add(new UserType("Provider"));
    userTypes.add(new UserType("Admin"));
    return userTypes;
  }

  private List<User> createDefaultUsers(UserTypeRepository userTypeRepository) {
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

  private List<CarManufacturer> createDefaultCarManufacturers() {
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

  private List<TransmissionType> createDefaultTransmissionTypes() {
    List<TransmissionType> transmissionTypes = new ArrayList<>();
    transmissionTypes.add(new TransmissionType("Manual"));
    transmissionTypes.add(new TransmissionType("Automatic"));
    return transmissionTypes;
  }

  private List<FuelType> createDefaultFuelTypes() {
    List<FuelType> fuelTypes = new ArrayList<>();
    fuelTypes.add(new FuelType("Petrol"));
    fuelTypes.add(new FuelType("Diesel"));
    fuelTypes.add(new FuelType("Electric"));
    return fuelTypes;
  }

  private List<Car> createDefaultCars(CarManufacturerRepository carManufacturerRepository,
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

  private List<Provider> createDefaultProviders() {
    List<Provider> providers = new ArrayList<>();
    providers.add(new Provider("Miller Bil", ADDRESS, "bil@miller.no"));
    providers.add(new Provider("Biller Bil", ADDRESS, "bil@biller.no"));
    providers.add(new Provider("Biggernes Tesla", ADDRESS, "biggernes@tesla.com"));
    providers.add(new Provider("Tesla Tom", ADDRESS, "tom.guldhav@outlook.com"));
    providers.add(new Provider("Auto 9-9", ADDRESS, "9@auto.no"));
    providers.add(new Provider("Auto 10-10", ADDRESS, "10@auto.no"));
    providers.add(new Provider("Bilikist", ADDRESS, "bilikist@bilikist.no"));
    providers.add(new Provider("Ørsta kommune", ADDRESS, "orsta@kommune.no"));
    providers.add(new Provider("Sirkelsliper", ADDRESS, "sirkelsliper@sliper.no"));
    providers.add(new Provider("Peace Per", ADDRESS, "peace@per.no"));
    providers.add(new Provider("Bilverksted", ADDRESS, "bilverksted@verksted.no"));
    providers.add(new Provider("Grabes", ADDRESS, "grabes@grabes.no"));
    providers.add(new Provider("Djarney", ADDRESS, "djarney@djarney.no"));
    providers.add(new Provider("Sprekksaver", ADDRESS, "sprekksaver@bil.no"));
    providers.add(new Provider("Smidig bilforhandler", ADDRESS, "smidig@bil.no"));
    providers.add(new Provider("Fossefall bilforhandler", ADDRESS, "fossefall@bil.no"));
    providers.add(new Provider("Betrel Ostein", ADDRESS, "betrel@ostein.no"));
    return providers;
  }

  private List<CarToProvider> createDefaultCarProviders(CarRepository carRepository,
                                                        ProviderRepository providerRepository) {
    List<CarToProvider> carProviders = new ArrayList<>();
    Car golf = carRepository.findByCarModel("Golf");
    Car model3 = carRepository.findByCarModel("Model 3");
    Car modelY = carRepository.findByCarModel("Model Y");
    Car leaf = carRepository.findByCarModel("Leaf");
    Car mazda2 = carRepository.findByCarModel("2");
    Car transporter = carRepository.findByCarModel("Transporter");
    Car m3 = carRepository.findByCarModel("M3 Evo");
    Car fabia = carRepository.findByCarModel("Fabia");
    Car peugeot307 = carRepository.findByCarModel("307 SW");
    Car peugeot207 = carRepository.findByCarModel("207");
    Car peugeot3008 = carRepository.findByCarModel("3008");
    Car peugeotiOn = carRepository.findByCarModel("iOn");


    Provider millerBil = providerRepository.findByName("Miller Bil");
    Provider billerBil = providerRepository.findByName("Biller Bil");
    Provider biggernesTesla = providerRepository.findByName("Biggernes Tesla");
    Provider teslaTom = providerRepository.findByName("Tesla Tom");
    Provider auto99 = providerRepository.findByName("Auto 9-9");
    Provider auto1010 = providerRepository.findByName("Auto 10-10");
    Provider bilikist = providerRepository.findByName("Bilikist");
    Provider orstaKommune = providerRepository.findByName("Ørsta kommune");
    Provider sirkelsliper = providerRepository.findByName("Sirkelsliper");
    Provider peacePer = providerRepository.findByName("Peace Per");
    Provider bilverksted = providerRepository.findByName("Bilverksted");
    Provider grabes = providerRepository.findByName("Grabes");
    Provider djarney = providerRepository.findByName("Djarney");
    Provider sprekksaver = providerRepository.findByName("Sprekksaver");
    Provider smidigBilforhandler = providerRepository.findByName("Smidig bilforhandler");
    Provider fossefallBilforhandler = providerRepository.findByName("Fossefall bilforhandler");
    Provider betrelOstein = providerRepository.findByName("Betrel Ostein");

    carProviders.add(new CarToProvider(golf, millerBil, 2, 600));
    carProviders.add(new CarToProvider(golf, billerBil, 2, 550));
    carProviders.add(new CarToProvider(model3, biggernesTesla, 3, 700));
    carProviders.add(new CarToProvider(model3, teslaTom, 1, 500));
    carProviders.add(new CarToProvider(modelY, biggernesTesla, 2, 900));
    carProviders.add(new CarToProvider(modelY, teslaTom, 1, 700));
    carProviders.add(new CarToProvider(leaf, auto99, 2, 500));
    carProviders.add(new CarToProvider(leaf, auto1010, 2, 500));
    carProviders.add(new CarToProvider(mazda2, bilikist, 3, 400));
    carProviders.add(new CarToProvider(transporter, orstaKommune, 1, 200));
    carProviders.add(new CarToProvider(transporter, sirkelsliper, 1, 70));
    carProviders.add(new CarToProvider(transporter, peacePer, 1, 180));
    carProviders.add(new CarToProvider(m3, bilverksted, 2, 400));
    carProviders.add(new CarToProvider(m3, grabes, 1, 450));
    carProviders.add(new CarToProvider(m3, djarney, 4, 449));
    carProviders.add(new CarToProvider(fabia, sprekksaver, 1, 300));
    carProviders.add(new CarToProvider(fabia, smidigBilforhandler, 2, 299));
    carProviders.add(new CarToProvider(fabia, fossefallBilforhandler, 3, 700));
    carProviders.add(new CarToProvider(peugeot307, betrelOstein, 2, 600));
    carProviders.add(new CarToProvider(peugeot307, auto1010, 3, 550));
    carProviders.add(new CarToProvider(peugeot207, betrelOstein, 1, 500));
    carProviders.add(new CarToProvider(peugeot207, auto1010, 2, 550));
    carProviders.add(new CarToProvider(peugeot3008, betrelOstein, 3, 600));
    carProviders.add(new CarToProvider(peugeot3008, auto1010, 4, 600));
    carProviders.add(new CarToProvider(peugeotiOn, betrelOstein, 5, 200));
    carProviders.add(new CarToProvider(peugeotiOn, auto1010, 1, 201));

    return carProviders;
  }
}
