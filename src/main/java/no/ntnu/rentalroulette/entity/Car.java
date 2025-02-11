package no.ntnu.rentalroulette.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
   * Entity's row ID in Database.
   *
   * @return id
   */
  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  /**
   * Entity car model
   *
   * @param carModel
   * @return carModel
   */
  @Setter
  @Getter
  @Column(name = "car_model")
  private String carModel;

  @Getter
  @ManyToOne
  @JoinColumn(name = "manufacturer_id", nullable = false)
  @JsonIgnore
  private CarManufacturer manufacturer;

  /**
   * Entiity number_of_seats this car entity has.
   *
   * @param numberOfSeats
   * @return numberOfSeats
   */
  @Setter
  @Getter
  @Column(name = "number_of_seats")
  private int numberOfSeats;

  /**
   * Car entity's transmission type.
   *
   * @param transmissionType
   * @return transmissionType
   */
  @Setter
  @Getter
  @Column(name = "transmission_type")
  private String transmissionType;

  /**
   * Car entity's fuel type.
   *
   * @param fuelType
   * @return fuelType
   */
  @Setter
  @Getter
  @Column(name = "fuel_type")
  private String fuelType;
  /**
   * Car entity's production year.
   *
   * @param productionYear
   * @return productionYear
   */
  @Setter
  @Getter
  @Column(name = "production_year")
  private int productionYear;

  public Car(
      String model,
      CarManufacturer manufacturer,
      int seats,
      String transmissionType,
      String fuelType,
      int productionYear) {
    this.carModel = model;
    this.manufacturer = manufacturer;
    this.numberOfSeats = seats;
    this.transmissionType = transmissionType;
    this.fuelType = fuelType;
    this.productionYear = productionYear;
  }
}
