package no.ntnu.rentalroulette;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

/**
 *
 */
@Entity
@Table(name = "car")
public class Car {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "car_model")
  private String carModel;
  @Column(name = "number_of_seats")
  private int numberOfSeats;
  @Column(name = "transmission_type")
  private String transmissionType;
  @Column(name = "fuel_type")
  private String fuelType;
  @Column(name = "year")
  private int productionYear;

  public Car() {
  }

  public Car(
      String model,
      int seats,
      String transmissionType,
      String fuelType,
      int productionYear) {
    this.carModel = model;
    this.numberOfSeats = seats;
    this.transmissionType = transmissionType;
    this.fuelType = fuelType;
    this.productionYear = productionYear;
  }

  /**
   * Get Entity's row ID in Database.
   *
   * @return id
   */
  public int getId() {
    return id;
  }


  /**
   * Get Entity car model.
   *
   * @return car model
   */
  public String getCarModel() {
    return carModel;
  }


  /**
   * Get number of seats this car entity has
   *
   * @return number of seats
   */
  public int getNumberOfSeats() {
    return numberOfSeats;
  }


  /**
   * Get car entity's transmission type.
   *
   * @return transmission type
   */
  public String getTransmissionType() {
    return transmissionType;
  }


  /**
   * Get car entity's fuel type.
   *
   * @return fuel type
   */
  public String getFuelType() {
    return fuelType;
  }


  /**
   * Get car entity's production year.
   *
   * @return production year
   */
  public int getProductionYear() {
    return productionYear;
  }

}
