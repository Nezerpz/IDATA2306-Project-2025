package no.ntnu.rentalroulette.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
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
  @JsonProperty("manufacturer")
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
  @ManyToOne
  @JoinColumn(name = "transmission_type_id", nullable = false)
  @JsonProperty("transmissionType")
  private TransmissionType transmissionType;

  /**
   * Car entity's fuel type.
   *
   * @param fuelType
   * @return fuelType
   */
  @Setter
  @Getter
  @ManyToOne
  @JoinColumn(name = "fuel_type_id", nullable = false)
  @JsonProperty("fuelType")
  private FuelType fuelType;
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


  @Getter
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "car_feature",
      joinColumns = @JoinColumn(name = "car_id"),
      inverseJoinColumns = @JoinColumn(name = "feature_id")
  )
  @JsonProperty("features")
  private List<Feature> features = new ArrayList<>();


  public Car(
      String model,
      CarManufacturer manufacturer,
      int seats,
      TransmissionType transmissionType,
      FuelType fuelType,
      int productionYear) {
    this.carModel = model;
    this.manufacturer = manufacturer;
    this.numberOfSeats = seats;
    this.transmissionType = transmissionType;
    this.fuelType = fuelType;
    this.productionYear = productionYear;
  }

  @JsonProperty("manufacturer")
  public String getManufacturerName() {
    return manufacturer.getName();
  }

  @JsonProperty("transmissionType")
  public String getTransmissionTypeName() {
    return transmissionType.getTransmissionType();
  }

  @JsonProperty("fuelType")
  public String getFuelTypeName() {
    return fuelType.getFuelType();
  }

  @JsonProperty("features")
  public List<String> getFeatureNames() {
    return features.stream().map(Feature::getFeatureName).toList();
  }
}
