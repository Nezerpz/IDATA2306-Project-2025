package no.ntnu.rentalroulette.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "car")
public class Car {
  /**
   * -- SETTER --
   * Set Entity's row ID in Database.
   *
   * @param id
   */
  @Setter
  /**
   * -- GETTER --
   * Get Entity's row ID in Database.
   *
   * @return id
   */
  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  /**
   * -- SETTER --
   * Set Entity car model.
   *
   * @param carModel
   */
  @Setter
  /**
   * -- GETTER --
   * Get Entity car model.
   *
   * @return car model
   */
  @Getter
  @Column(name = "car_model")
  private String carModel;

  /**
   * -- SETTER --
   * Set number of seats this car entity has
   *
   * @param numberOfSeats
   */
  @Setter
  /**
   * -- GETTER --
   * Get number of seats this car entity has
   *
   * @return number of seats
   */
  @Getter
  @Column(name = "number_of_seats")
  private int numberOfSeats;

  /**
   * -- SETTER --
   * Set car entity's transmission type.
   *
   * @param transmissionType
   */
  @Setter
  /**
   * -- GETTER --
   * Get car entity's transmission type.
   *
   * @return transmission type
   */
  @Getter
  @Column(name = "transmission_type")
  private String transmissionType;

  /**
   * -- SETTER --
   * Set car entity's fuel type.
   *
   * @param fuelType
   */
  @Setter
  /**
   * -- GETTER --
   * Get car entity's fuel type.
   *
   * @return fuel type
   */
  @Getter
  @Column(name = "fuel_type")
  private String fuelType;
  /**
   * -- SETTER --
   * Set car entity's production year.
   *
   * @param productionYear
   */
  @Setter
  /**
   * -- GETTER --
   * Get car entity's production year.
   *
   * @return production year
   */
  @Getter
  @Column(name = "production_year")
  private int productionYear;

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
}