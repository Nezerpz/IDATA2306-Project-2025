package no.ntnu.rentalroulette.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.ntnu.rentalroulette.enums.CarStatus;
import no.ntnu.rentalroulette.enums.FuelType;
import no.ntnu.rentalroulette.enums.Manufacturer;
import no.ntnu.rentalroulette.enums.TransmissionType;

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

  @Setter
  @Getter
  @Column(name = "image_path")
  private String imagePath;

  @Getter
  @Enumerated(EnumType.STRING)
  private Manufacturer manufacturer;

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

  /**
   * Entity price this car entity has.
   *
   * @param price
   * @return price
   */
  @Setter
  @Getter
  @Column(name = "price")
  private int price;

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
  @Enumerated(EnumType.STRING)
  private TransmissionType transmissionType;

  /**
   * Car entity's fuel type.
   *
   * @param fuelType
   * @return fuelType
   */
  @Setter
  @Getter
  @Enumerated(EnumType.STRING)
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

  @Setter
  @Getter
  @Column(name = "car_status")
  @Enumerated(EnumType.STRING)
  private CarStatus carStatus;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonProperty("user")
  private User user;


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
      String imagePath,
      String model,
      Manufacturer manufacturer,
      int seats,
      TransmissionType transmissionType,
      FuelType fuelType,
      int price,
      int productionYear) {
    this.imagePath = imagePath;
    this.carModel = model;
    this.manufacturer = manufacturer;
    this.numberOfSeats = seats;
    this.transmissionType = transmissionType;
    this.fuelType = fuelType;
    this.price = price;
    this.productionYear = productionYear;
  }

  @JsonProperty("features")
  public List<String> getFeatureNames() {
    return features.stream().map(Feature::getFeatureName).toList();
  }

  @JsonProperty("user")
  public String getProviderName() {
    StringBuilder sb = new StringBuilder();
    sb.append(user.getFirstName());
    sb.append(" ");
    sb.append(user.getLastName());
    String providerName = sb.toString();
    return providerName;
  }
}
