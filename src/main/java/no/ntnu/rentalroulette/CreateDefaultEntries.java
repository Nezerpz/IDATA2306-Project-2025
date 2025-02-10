package no.ntnu.rentalroulette;

import java.util.ArrayList;
import java.util.List;

public class CreateDefaultEntries {
  public void createDefaultEntries() {

  }

  public List<Car> createDefaultCars() {
    List<Car> cars = new ArrayList<>();
    cars.add(new Car("Golf", 5, "Manual", "Petrol", 2007));
    cars.add(new Car("Model 3", 5, "Automatic", "Electric", 2019));
    cars.add(new Car("Model Y", 5, "Automatic", "Electric", 2022));
    cars.add(new Car("Leaf", 5, "Automatic", "Electric", 2016));
    cars.add(new Car("2", 5, "Automatic", "Petrol", 2017));
    cars.add(new Car("Transporter", 8, "Manual", "Petrol", 1978));
    cars.add(new Car("M3 Evo", 4, "Manual", "Petrol", 1988));
    cars.add(new Car("Fabia", 5, "Automatic", "Diesel", 2011));
    cars.add(new Car("307 SW", 7, "Manual", "Diesel", 2008));
    cars.add(new Car("207", 5, "Manual", "Diesel", 2007));
    cars.add(new Car("3008", 5, "Manual", "Diesel", 2010));
    cars.add(new Car("iOn", 4, "Automatic", "Electric", 2015));
    return cars;
  }

  //TODO: Add usertype gotten from the database.
  public List<User> createDefaultUsers() {
    List<User> users = new ArrayList<>();
    users.add(new User(new UserType("Customer"), "Ola", "Nordmann", "ola.nordmann", "teletubbies",
        "ola.nordmann@telenor.no"));
    return users;
  }

  public void createDefaultProviders() {

  }

  public void createDefaultOrders() {
    // Create default orders
  }

  public List<UserType> createDefaultUserTypes() {
    List<UserType> userTypes = new ArrayList<>();
    userTypes.add(new UserType("Customer"));
    //TODO: Find out if -> userTypes.add(new UserType("Provider"));
    userTypes.add(new UserType("Admin"));
    return userTypes;
  }


  public List<CarManufacturer> createDefaultManufacturers() {
    List<CarManufacturer> carManufacturers = new ArrayList<>();
    carManufacturers.add(new CarManufacturer("Volkswagen"));
    carManufacturers.add(new CarManufacturer("Tesla"));
    carManufacturers.add(new CarManufacturer("Nissan"));
    carManufacturers.add(new CarManufacturer("Peugeot"));
    carManufacturers.add(new CarManufacturer("BMW"));
    carManufacturers.add(new CarManufacturer("Skoda"));
    carManufacturers.add(new CarManufacturer("Mazda"));
    return carManufacturers;
  }
}
