package no.ntnu.rentalroulette;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.entity.CarReview;
import no.ntnu.rentalroulette.entity.Feature;
import no.ntnu.rentalroulette.entity.Order;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.entity.UserReview;
import no.ntnu.rentalroulette.enums.CarStatus;
import no.ntnu.rentalroulette.enums.FuelType;
import no.ntnu.rentalroulette.enums.Manufacturer;
import no.ntnu.rentalroulette.enums.OrderStatus;
import no.ntnu.rentalroulette.enums.TransmissionType;
import no.ntnu.rentalroulette.enums.UserType;
import no.ntnu.rentalroulette.repository.CarRepository;
import no.ntnu.rentalroulette.repository.CarReviewRepository;
import no.ntnu.rentalroulette.repository.FeatureRepository;
import no.ntnu.rentalroulette.repository.OrderRepository;
import no.ntnu.rentalroulette.repository.UserRepository;
import no.ntnu.rentalroulette.repository.UserReviewRepository;
import org.springframework.context.ApplicationContext;

public class SampleDataGenerator {
  private ApplicationContext context;
  private static final String ADDRESS = "Smibakken 1, Verftsgata 2, 6018 Ålesund";

  //TODO: Use the database scheme to create the two remaining tables for some reviews.

  public SampleDataGenerator(ApplicationContext context) {
    this.context = context;
  }

  @Transactional
  public void createDefaultEntries() {

    // Users

    UserRepository userRepository = this.context.getBean(UserRepository.class);
    List<User> users = createDefaultUsers();
    userRepository.saveAll(users);

    // Cars
    CarRepository carRepository = this.context.getBean(CarRepository.class);
    List<Car> cars = createDefaultCars(userRepository);
    updateCarStatus(cars, CarStatus.AVAILABLE);
    carRepository.saveAll(cars);

    // Features
    FeatureRepository featureRepository = this.context.getBean(FeatureRepository.class);
    createDefaultFeatures(carRepository, featureRepository);

    // Orders
    OrderRepository orderRepository = this.context.getBean(OrderRepository.class);
    createDefaultOrders(carRepository, userRepository, orderRepository);

    // Reviews
    UserReviewRepository userReviewRepository = this.context.getBean(UserReviewRepository.class);
    CarReviewRepository carReviewRepository = this.context.getBean(CarReviewRepository.class);
    createDefaultReviews(userRepository, carRepository, carReviewRepository, userReviewRepository);
  }

  public static void updateCarStatus(List<Car> cars, CarStatus status) {
    for (Car car : cars) {
      car.setCarStatus(status);
    }
  }


  private List<User> createDefaultUsers() {
    List<User> users = new ArrayList<>();
    users.add(new User(UserType.CUSTOMER, "Ola", "Nordmann", "ola.nordmann", "teletubbies",
        "ola.nordmann@telenor.no", "+47 12345678"));
    users.add(new User(UserType.CUSTOMER, "Kari", "Nordmann", "kari.nordmann", "cars",
        "kari.nordmann@telenor.no", "+47 87654321"));
    users.add(new User(UserType.ADMIN, "Jeremy", "Clarkson", "jClarkson", "france",
        "jClarkson@rentalroulette.fr", "+47 23456789"));
    users.add(new User(UserType.PROVIDER, "Miller", "Bil", "miller.bil", "password",
        "miller.bil@example.com", "+47 98765432"));
    users.add(new User(UserType.PROVIDER, "Biller", "Bil", "biller.bil", "password",
        "biller.bil@example.com", "+47 86754321"));
    users.add(new User(UserType.PROVIDER, "Biggernes", "Tesla", "biggernes.tesla", "password",
        "biggernes.tesla@example.com", "+47 76543210"));
    users.add(new User(UserType.PROVIDER, "Tesla", "Tom", "tesla.tom", "password",
        "tesla.tom@example.com", "+47 65432109"));
    users.add(
        new User(UserType.PROVIDER, "Auto", "9-9", "auto.9-9", "password", "auto.9-9@example.com",
            "+47 54321098"));
    users.add(new User(UserType.PROVIDER, "Auto", "10-10", "auto.10-10", "password",
        "auto.10-10@example.com", "+47 43210987"));
    users.add(new User(UserType.PROVIDER, "Bilikist", "", "bilikist", "password",
        "bilikist@example.com", "+47 32109876"));
    users.add(new User(UserType.PROVIDER, "Ørsta", "kommune", "orsta.kommune", "password",
        "orsta.kommune@example.com", "+47 21098765"));
    users.add(new User(UserType.PROVIDER, "Sirkelsliper", "", "sirkelsliper", "password",
        "sirkelsliper@example.com", "+47 10987654"));
    users.add(new User(UserType.PROVIDER, "Peace", "Per", "peace.per", "password",
        "peace.per@example.com", "+47 09876543"));
    users.add(new User(UserType.PROVIDER, "Bilverksted", "", "bilverksted", "password",
        "bilverksted@example.com", "+47 97865432"));
    users.add(
        new User(UserType.PROVIDER, "Grabes", "", "grabes", "password", "grabes@example.com",
            "+47 87645321"));
    users.add(
        new User(UserType.PROVIDER, "Djarney", "", "djarney", "password", "djarney@example.com",
            "+47 76534210"));
    users.add(new User(UserType.PROVIDER, "Sprekksaver", "", "sprekksaver", "password",
        "sprekksaver@example.com", "+47 65423109"));
    users.add(
        new User(UserType.PROVIDER, "Smidig", "bilforhandler", "smidig.bilforhandler", "password",
            "smidig.bilforhandler@example.com", "+47 54231098"));
    users.add(new User(UserType.PROVIDER, "Fossefall", "bilforhandler", "fossefall.bilforhandler",
        "password", "fossefall.bilforhandler@example.com", "+47 43109876"));
    users.add(new User(UserType.PROVIDER, "Betrel", "Ostein", "betrel.ostein", "password",
        "betrel.ostein@example.com", "+47 31098765"));

    return users;
  }


  private List<Car> createDefaultCars(UserRepository userRepository) {
    List<Car> cars = new ArrayList<>();
    Manufacturer volkswagen = Manufacturer.VOLKSWAGEN;
    Manufacturer tesla = Manufacturer.TESLA;
    Manufacturer nissan = Manufacturer.NISSAN;
    Manufacturer bmw = Manufacturer.BMW;
    Manufacturer peugeot = Manufacturer.PEUGEOT;
    Manufacturer skoda = Manufacturer.SKODA;
    Manufacturer mazda = Manufacturer.MAZDA;
    TransmissionType manual = TransmissionType.MANUAL;
    TransmissionType automatic = TransmissionType.AUTOMATIC;
    FuelType petrol = FuelType.PETROL;
    FuelType diesel = FuelType.DIESEL;
    FuelType electric = FuelType.ELECTRIC;
    User millerBil = userRepository.findByUsername("miller.bil").get();
    User billerBil = userRepository.findByUsername("biller.bil").get();
    User biggernesTesla = userRepository.findByUsername("biggernes.tesla").get();
    User teslaTom = userRepository.findByUsername("tesla.tom").get();
    User auto99 = userRepository.findByUsername("auto.9-9").get();
    User auto1010 = userRepository.findByUsername("auto.10-10").get();
    User bilikist = userRepository.findByUsername("bilikist").get();
    User orstaKommune = userRepository.findByUsername("orsta.kommune").get();
    User sirkelsliper = userRepository.findByUsername("sirkelsliper").get();
    User peacePer = userRepository.findByUsername("peace.per").get();
    User bilverksted = userRepository.findByUsername("bilverksted").get();
    User grabes = userRepository.findByUsername("grabes").get();
    User djarney = userRepository.findByUsername("djarney").get();
    User sprekksaver = userRepository.findByUsername("sprekksaver").get();
    User smidigBilforhandler = userRepository.findByUsername("smidig.bilforhandler").get();
    User fossefallBilforhandler = userRepository.findByUsername("fossefall.bilforhandler").get();
    User betrelOstein = userRepository.findByUsername("betrel.ostein").get();

    Car golf1 = new Car("/user-uploads/Golf.jpg", "Golf", volkswagen, 5, manual, diesel, 600, 2007);
    cars.add(golf1);
    golf1.setUser(millerBil);

    Car golf2 = new Car("/user-uploads/Golf.jpg", "Golf", volkswagen, 5, manual, diesel, 550, 2007);
    cars.add(golf2);
    golf2.setUser(billerBil);

    Car model3_1 =
        new Car("/user-uploads/Model3.jpg", "Model 3", tesla, 5, automatic, electric, 700, 2019);
    cars.add(model3_1);
    model3_1.setUser(biggernesTesla);

    Car model3_2 =
        new Car("/user-uploads/Model3.jpg", "Model 3", tesla, 5, automatic, electric, 500, 2019);
    cars.add(model3_2);
    model3_2.setUser(teslaTom);

    Car modelY_1 =
        new Car("/user-uploads/ModelY.jpg", "Model Y", tesla, 5, automatic, electric, 900, 2022);
    cars.add(modelY_1);
    modelY_1.setUser(biggernesTesla);

    Car modelY_2 =
        new Car("/user-uploads/ModelY.jpg", "Model Y", tesla, 5, automatic, electric, 700, 2022);
    cars.add(modelY_2);
    modelY_2.setUser(teslaTom);

    Car leaf1 =
        new Car("/user-uploads/Leaf.jpg", "Leaf", nissan, 5, automatic, electric, 500, 2016);
    cars.add(leaf1);
    leaf1.setUser(auto99);

    Car leaf2 =
        new Car("/user-uploads/Leaf.jpg", "Leaf", nissan, 5, automatic, electric, 500, 2016);
    cars.add(leaf2);
    leaf2.setUser(auto1010);

    Car mazda2 = new Car("/user-uploads/Mazda2.jpg", "2", mazda, 5, automatic, petrol, 400, 2017);
    cars.add(mazda2);
    mazda2.setUser(bilikist);

    Car transporter1 =
        new Car("/user-uploads/Transporter.jpg", "Transporter", volkswagen, 8, manual, petrol, 200,
            1978);
    cars.add(transporter1);
    transporter1.setUser(orstaKommune);

    Car transporter2 =
        new Car("/user-uploads/Transporter.jpg", "Transporter", volkswagen, 8, manual, petrol, 70,
            1978);
    cars.add(transporter2);
    transporter2.setUser(sirkelsliper);

    Car transporter3 =
        new Car("/user-uploads/Transporter.jpg", "Transporter", volkswagen, 8, manual, petrol, 180,
            1978);
    cars.add(transporter3);
    transporter3.setUser(peacePer);

    Car m3_1 = new Car("/user-uploads/M3Evo.jpg", "M3 Evo", bmw, 4, manual, petrol, 400, 1988);
    cars.add(m3_1);
    m3_1.setUser(bilverksted);

    Car m3_2 = new Car("/user-uploads/M3Evo.jpg", "M3 Evo", bmw, 4, manual, petrol, 450, 1988);
    cars.add(m3_2);
    m3_2.setUser(grabes);

    Car m3_3 = new Car("/user-uploads/M3Evo.jpg", "M3 Evo", bmw, 4, manual, petrol, 449, 1988);
    cars.add(m3_3);
    m3_3.setUser(djarney);

    Car fabia1 =
        new Car("/user-uploads/Fabia.jpg", "Fabia", skoda, 5, automatic, diesel, 300, 2011);
    cars.add(fabia1);
    fabia1.setUser(sprekksaver);

    Car fabia2 =
        new Car("/user-uploads/Fabia.jpg", "Fabia", skoda, 5, automatic, diesel, 299, 2011);
    cars.add(fabia2);
    fabia2.setUser(smidigBilforhandler);

    Car fabia3 =
        new Car("/user-uploads/Fabia.jpg", "Fabia", skoda, 5, automatic, diesel, 700, 2011);
    cars.add(fabia3);
    fabia3.setUser(fossefallBilforhandler);

    Car peugeot307_1 =
        new Car("/user-uploads/307SW.jpg", "307 SW", peugeot, 7, manual, diesel, 600, 2008);
    cars.add(peugeot307_1);
    peugeot307_1.setUser(betrelOstein);

    Car peugeot307_2 =
        new Car("/user-uploads/307SW.jpg", "307 SW", peugeot, 7, manual, diesel, 550, 2008);
    cars.add(peugeot307_2);
    peugeot307_2.setUser(auto1010);

    Car peugeot207_1 =
        new Car("/user-uploads/207.jpg", "207", peugeot, 5, manual, diesel, 500, 2007);
    cars.add(peugeot207_1);
    peugeot207_1.setUser(betrelOstein);

    Car peugeot207_2 =
        new Car("/user-uploads/207.jpg", "207", peugeot, 5, manual, diesel, 550, 2007);
    cars.add(peugeot207_2);
    peugeot207_2.setUser(auto1010);

    Car peugeot3008_1 =
        new Car("/user-uploads/3008.jpg", "3008", peugeot, 5, manual, diesel, 600, 2010);
    cars.add(peugeot3008_1);
    peugeot3008_1.setUser(betrelOstein);

    Car peugeot3008_2 =
        new Car("/user-uploads/3008.jpg", "3008", peugeot, 5, manual, diesel, 600, 2010);
    cars.add(peugeot3008_2);
    peugeot3008_2.setUser(auto1010);

    Car iOn1 = new Car("/user-uploads/iOn.jpg", "iOn", peugeot, 4, automatic, electric, 200, 2015);
    cars.add(iOn1);
    iOn1.setUser(betrelOstein);

    Car iOn2 = new Car("/user-uploads/iOn.jpg", "iOn", peugeot, 4, automatic, electric, 201, 2015);
    cars.add(iOn2);
    iOn2.setUser(auto1010);

    Car iOn3 = new Car("/user-uploads/iOn.jpg", "iOn", peugeot, 4, automatic, electric, 200, 2015);
    cars.add(iOn3);
    iOn3.setUser(betrelOstein);

    Car iOn4 = new Car("/user-uploads/iOn.jpg", "iOn", peugeot, 4, automatic, electric, 201, 2015);
    cars.add(iOn4);
    iOn4.setUser(auto1010);

    Car iOn5 = new Car("/user-uploads/iOn.jpg", "iOn", peugeot, 4, automatic, electric, 201, 2015);
    cars.add(iOn5);
    iOn5.setUser(auto1010);

    return cars;
  }


  private void createDefaultOrders(CarRepository carRepository,
                                   UserRepository userRepository, OrderRepository orderRepository) {
    List<Order> orders = new ArrayList<>();
    User customer1 = userRepository.findByUsername("ola.nordmann").get();
    User customer2 = userRepository.findByUsername("kari.nordmann").get();

    // Some cars may be busy in some periods for one provider, but always available from another supplier

    LocalDate startDateOneProvider = LocalDate.of(2025, 1, 1);
    LocalDate endDateOneProvider = LocalDate.of(2025, 6, 10);
    LocalTime startTime = LocalTime.of(8, 0);
    LocalTime endTime = LocalTime.of(17, 0);
    Car golf1 = carRepository.findById(1);
    User golf1Provider = userRepository.findById(golf1.getUser().getId()).orElse(null);

    orders.add(
        new Order(customer1, golf1Provider, startDateOneProvider, endDateOneProvider, startTime,
            endTime,
            String.valueOf(golf1.getPrice() *
                ChronoUnit.DAYS.between(startDateOneProvider, endDateOneProvider)), golf1,
            OrderStatus.ONGOING));
    golf1.setCarStatus(CarStatus.INUSE);

// Some cars may be busy from all providers for some weeks
    List<Car> transporters = carRepository.findByCarModel("Transporter");

    LocalDate startDateSomeWeeks = LocalDate.of(2025, 3, 1);
    LocalDate endDateSomeWeeks = LocalDate.of(2025, 3, 15);
    Car transporter1 = transporters.get(0);
    Car transporter2 = transporters.get(1);
    Car transporter3 = transporters.get(2);
    User transporter1Provider =
        userRepository.findById(transporter1.getUser().getId()).orElse(null);
    User transporter2Provider =
        userRepository.findById(transporter2.getUser().getId()).orElse(null);
    User transporter3Provider =
        userRepository.findById(transporter3.getUser().getId()).orElse(null);

    orders.add(
        new Order(customer1, transporter1Provider, startDateSomeWeeks, endDateSomeWeeks, startTime,
            endTime,
            String.valueOf(transporter1.getPrice() *
                ChronoUnit.DAYS.between(startDateSomeWeeks, endDateSomeWeeks)), transporter1,
            OrderStatus.ONGOING));
    transporter1.setCarStatus(CarStatus.INUSE);
    orders.add(
        new Order(customer2, transporter2Provider, startDateSomeWeeks, endDateSomeWeeks, startTime,
            endTime,
            String.valueOf(transporter2.getPrice() *
                ChronoUnit.DAYS.between(startDateSomeWeeks, endDateSomeWeeks)), transporter2,
            OrderStatus.ONGOING));
    transporter2.setCarStatus(CarStatus.INUSE);
    orders.add(
        new Order(customer2, transporter3Provider, startDateSomeWeeks, endDateSomeWeeks, startTime,
            endTime,
            String.valueOf(transporter3.getPrice() *
                ChronoUnit.DAYS.between(startDateSomeWeeks, endDateSomeWeeks)), transporter3,
            OrderStatus.ONGOING));
    transporter3.setCarStatus(CarStatus.INUSE);

// Some cars may be “fully booked” out of 2025
    List<Car> iOns = carRepository.findByCarModel("iOn");

    LocalDate startDateFullyBooked = LocalDate.of(2025, 1, 1);
    LocalDate endDateFullyBooked = LocalDate.of(2025, 12, 31);
    Car iOn1 = iOns.get(0);
    Car iOn2 = iOns.get(1);
    Car iOn3 = iOns.get(2);
    Car iOn4 = iOns.get(3);
    Car iOn5 = iOns.get(4);
    User iOn1Provider = userRepository.findById(iOn1.getUser().getId()).orElse(null);
    User iOn2Provider = userRepository.findById(iOn2.getUser().getId()).orElse(null);
    User iOn3Provider = userRepository.findById(iOn3.getUser().getId()).orElse(null);
    User iOn4Provider = userRepository.findById(iOn4.getUser().getId()).orElse(null);
    User iOn5Provider = userRepository.findById(iOn5.getUser().getId()).orElse(null);

    orders.add(
        new Order(customer1, iOn1Provider, startDateFullyBooked, endDateFullyBooked, startTime,
            endTime,
            String.valueOf(iOn1.getPrice() *
                ChronoUnit.DAYS.between(startDateFullyBooked, endDateFullyBooked)), iOn1,
            OrderStatus.ONGOING));
    iOn1.setCarStatus(CarStatus.INUSE);
    orders.add(
        new Order(customer1, iOn2Provider, startDateFullyBooked, endDateFullyBooked, startTime,
            endTime,
            String.valueOf(iOn2.getPrice() *
                ChronoUnit.DAYS.between(startDateFullyBooked, endDateFullyBooked)), iOn2,
            OrderStatus.ONGOING));
    iOn2.setCarStatus(CarStatus.INUSE);
    orders.add(
        new Order(customer1, iOn3Provider, startDateFullyBooked, endDateFullyBooked, startTime,
            endTime,
            String.valueOf(iOn3.getPrice() *
                ChronoUnit.DAYS.between(startDateFullyBooked, endDateFullyBooked)), iOn3,
            OrderStatus.ONGOING));
    iOn3.setCarStatus(CarStatus.INUSE);
    orders.add(
        new Order(customer2, iOn4Provider, startDateFullyBooked, endDateFullyBooked, startTime,
            endTime,
            String.valueOf(iOn4.getPrice() *
                ChronoUnit.DAYS.between(startDateFullyBooked, endDateFullyBooked)), iOn4,
            OrderStatus.ONGOING));
    iOn4.setCarStatus(CarStatus.INUSE);
    orders.add(
        new Order(customer2, iOn5Provider, startDateFullyBooked, endDateFullyBooked, startTime,
            endTime,
            String.valueOf(iOn5.getPrice() *
                ChronoUnit.DAYS.between(startDateFullyBooked, endDateFullyBooked)), iOn5,
            OrderStatus.ONGOING));
    iOn5.setCarStatus(CarStatus.INUSE);
    carRepository.saveAll(List.of(golf1, transporter1, transporter2, transporter3, iOn1, iOn2, iOn3,
        iOn4, iOn5));
    orderRepository.saveAll(orders);

    for (Order order : orders){
      if (order.getDateTo().isBefore(LocalDate.now()) && order.getOrderStatus() == OrderStatus.ONGOING) {
        order.setOrderStatus(OrderStatus.COMPLETED);
        Car car = order.getCar();
        car.setCarStatus(CarStatus.AVAILABLE);
        carRepository.save(car);
        orderRepository.save(order);
      }
    }
  }

  private void createDefaultFeatures(CarRepository carRepository,
                                     FeatureRepository featureRepository) {

    Feature sunroof = new Feature("Sunroof",
        "The sunroof allows you to enjoy the sun and fresh air while driving.");
    Feature heatedSeats =
        new Feature("Heated Seats", "Heated seats keep you warm during cold days.");
    Feature dabRadio =
        new Feature("DAB Radio", "DAB Radio provides a better sound quality than FM Radio.");
    Feature autonomousDriving =
        new Feature("Autonomous Driving", "This car can drive itself! (almost)");
    Feature longRange = new Feature("Long Range",
        "This car can drive a long distance before needing to be recharged.");
    Feature fourWheelDrive =
        new Feature("Four Wheel Drive", "Four Wheel Drive provides better traction and control.");
    Feature yellowPaint = new Feature("Yellow Paint", "Yellow paint makes the car stand out.");
    Feature retroDesign =
        new Feature("Retro Design", "Retro design gives the car a classic and cool look.");
    Feature threeStripes = new Feature("Three Stripes", "Three stripes make the car look sporty.");
    Feature originalTireDiscs =
        new Feature("Original Tire Discs", "Original tire discs make the car look cool.");
    Feature towHook = new Feature("Tow Hook", "Tow hook can be used to tow other vehicles.");
    Feature roofBox = new Feature("Roof Box", "Roof box provides extra storage space.");
    Feature glassWindow = new Feature("Glass Window", "Glass window provides a better view.");
    Feature heatedSteeringWheel =
        new Feature("Heated Steering Wheel", "Heated steering wheel keeps your hands warm.");
    Feature heatedMirrors =
        new Feature("Heated Mirrors", "Heated mirrors keep your mirrors clear.");
    Feature heatedTires = new Feature("Heated Tires", "Heated tires provide better traction.");
    Feature heatedRug = new Feature("Heated Rug", "Heated rug keeps your feet warm.");
    Feature heated360 = new Feature("Heated 360", "Heated 360 keeps the whole car warm.");
    Feature fMRadio = new Feature("FM Radio", "FM Radio provides a good sound quality.");
    Feature cDPlayer = new Feature("CD Player", "CD Player allows you to play CDs.");
    Feature metallicPaint =
        new Feature("Metallic Paint", "Metallic paint makes the car look shiny.");
    Feature fiveDoors = new Feature("Five Doors", "Five doors provide easy access to the car.");
    Feature veryEconomical = new Feature("Very Economical", "This car is very economical.");
    Feature bluetooth =
        new Feature("Bluetooth", "Bluetooth allows you to connect your phone to the car.");

    List<Feature> features =
        new ArrayList<>(List.of(sunroof, heatedSeats, dabRadio, autonomousDriving, longRange,
            fourWheelDrive, yellowPaint, retroDesign, threeStripes, originalTireDiscs, towHook,
            roofBox, glassWindow, heatedSteeringWheel, heatedMirrors, heatedTires, heatedRug,
            heated360,
            fMRadio, cDPlayer, metallicPaint, fiveDoors, veryEconomical, bluetooth));


    featureRepository.saveAll(features);

    List<Car> golfCars = carRepository.findByCarModel("Golf");
    List<Car> model3Cars = carRepository.findByCarModel("Model 3");
    List<Car> modelYCars = carRepository.findByCarModel("Model Y");
    List<Car> leafCars = carRepository.findByCarModel("Leaf");
    List<Car> mazda2Cars = carRepository.findByCarModel("2");
    List<Car> transporterCars = carRepository.findByCarModel("Transporter");
    List<Car> m3Cars = carRepository.findByCarModel("M3 Evo");
    List<Car> fabiaCars = carRepository.findByCarModel("Fabia");
    List<Car> peugeot307Cars = carRepository.findByCarModel("307 SW");
    List<Car> peugeot207Cars = carRepository.findByCarModel("207");
    List<Car> peugeot3008Cars = carRepository.findByCarModel("3008");
    List<Car> peugeotiOnCars = carRepository.findByCarModel("iOn");

    for (Car car : golfCars) {
      car.getFeatures().addAll(List.of(heatedSeats, dabRadio, bluetooth));
    }

    for (Car car : model3Cars) {
      car.getFeatures().addAll(List.of(autonomousDriving, longRange, heatedSeats));
    }

    for (Car car : modelYCars) {
      car.getFeatures().addAll(List.of(fourWheelDrive, sunroof, autonomousDriving));
    }

    for (Car car : mazda2Cars) {
      car.getFeatures().add(dabRadio);
    }

    for (Car car : transporterCars) {
      car.getFeatures().addAll(List.of(yellowPaint, retroDesign));
    }

    for (Car car : m3Cars) {
      car.getFeatures().addAll(List.of(threeStripes, originalTireDiscs));
    }

    for (Car car : fabiaCars) {
      car.getFeatures().add(towHook);
    }

    for (Car car : peugeot307Cars) {
      car.getFeatures().add(roofBox);
    }

    for (Car car : peugeot207Cars) {
      car.getFeatures().addAll(List.of(glassWindow, heatedSeats, heatedSteeringWheel, heatedMirrors,
          heatedTires, heatedRug, heated360));
    }

    for (Car car : peugeot3008Cars) {
      car.getFeatures().addAll(List.of(fMRadio, cDPlayer, metallicPaint));
    }

    for (Car car : peugeotiOnCars) {
      car.getFeatures().addAll(List.of(fiveDoors, veryEconomical));
    }

    List<Car> allCars = new ArrayList<>();
    allCars.addAll(golfCars);
    allCars.addAll(model3Cars);
    allCars.addAll(modelYCars);
    allCars.addAll(leafCars);
    allCars.addAll(mazda2Cars);
    allCars.addAll(transporterCars);
    allCars.addAll(m3Cars);
    allCars.addAll(fabiaCars);
    allCars.addAll(peugeot307Cars);
    allCars.addAll(peugeot207Cars);
    allCars.addAll(peugeot3008Cars);
    allCars.addAll(peugeotiOnCars);

    carRepository.saveAll(allCars);
  }

  private void createDefaultReviews(UserRepository userRepository, CarRepository carRepository,
                                    CarReviewRepository carReviewRepository,
                                    UserReviewRepository userReviewRepository) {
    User olaNordmann = userRepository.findByUsername("ola.nordmann").get();
    User kariNordmann = userRepository.findByUsername("kari.nordmann").get();

    // Fetch specific cars
    Car golf1 = carRepository.findById(1);
    Car model3_1 = carRepository.findById(3);
    Car transporter1 = carRepository.findById(10);
    Car peugeot207_1 = carRepository.findById(20);

    // Car reviews
    List<CarReview> carReviews = new ArrayList<>();
    if (golf1 != null) {
      carReviews.add(new CarReview(olaNordmann, golf1, 5, "Excellent car for long trips!"));
      carReviews.add(new CarReview(kariNordmann, golf1, 4, "Comfortable and reliable."));
    }
    if (model3_1 != null) {
      carReviews.add(new CarReview(olaNordmann, model3_1, 5, "Amazing electric car!"));
    }
    if (transporter1 != null) {
      carReviews.add(new CarReview(kariNordmann, transporter1, 3, "Decent but old."));
    }
    if (peugeot207_1 != null) {
      carReviews.add(new CarReview(olaNordmann, peugeot207_1, 4, "Good for city driving."));
      carReviews.add(
          new CarReview(kariNordmann, peugeot207_1, 5, "Perfect for small families."));
    }
    carReviewRepository.saveAll(carReviews);

    // Fetch specific providers
    User millerBil = userRepository.findByUsername("miller.bil").get();
    User billerBil = userRepository.findByUsername("biller.bil").get();
    User biggernesTesla = userRepository.findByUsername("biggernes.tesla").get();
    User teslaTom = userRepository.findByUsername("tesla.tom").get();
    User auto99 = userRepository.findByUsername("auto.9-9").get();
    User auto1010 = userRepository.findByUsername("auto.10-10").get();
    User bilikist = userRepository.findByUsername("bilikist").get();
    User orstaKommune = userRepository.findByUsername("orsta.kommune").get();

    // Provider reviews
    List<UserReview> providerReviews = new ArrayList<>();
    providerReviews.add(
        new UserReview(millerBil, olaNordmann, 5, "Great service and friendly staff!"));
    providerReviews.add(new UserReview(millerBil, kariNordmann, 4, "Reliable provider."));
    providerReviews.add(new UserReview(billerBil, olaNordmann, 3, "Average experience."));
    providerReviews.add(
        new UserReview(biggernesTesla, kariNordmann, 5, "Excellent Tesla provider!"));
    providerReviews.add(
        new UserReview(teslaTom, olaNordmann, 4, "Good selection of electric cars."));
    providerReviews.add(new UserReview(auto99, kariNordmann, 5, "Affordable and efficient!"));
    providerReviews.add(new UserReview(auto1010, olaNordmann, 4, "Decent service."));
    providerReviews.add(
        new UserReview(bilikist, kariNordmann, 3, "Could improve customer support."));
    providerReviews.add(
        new UserReview(orstaKommune, olaNordmann, 5, "Excellent municipal provider!"));

    userReviewRepository.saveAll(providerReviews);

    //Customer reviews
    List<UserReview> customerReviews = new ArrayList<>();
    customerReviews.add(new UserReview(olaNordmann, millerBil, 5, "Great customer!"));
    customerReviews.add(new UserReview(kariNordmann, millerBil, 4, "Good customer."));
    customerReviews.add(new UserReview(olaNordmann, billerBil, 3, "Average customer."));
    customerReviews.add(new UserReview(kariNordmann, billerBil, 5, "Excellent customer!"));
    customerReviews.add(new UserReview(olaNordmann, biggernesTesla, 4, " Good customer!"));
    customerReviews.add(new UserReview(kariNordmann, biggernesTesla, 5, "Great customer!"));

    userReviewRepository.saveAll(customerReviews);
  }
}
