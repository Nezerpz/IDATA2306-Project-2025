package no.ntnu.rentalroulette.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "car_manufacturer")
public class CarManufacturer {
  @Id
  /**
   * Entity's row ID in Database.
   *
   * @return id
   */
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  /**
   * Entity's manufacturer name.
   *
   * @param manufacturerName
   * @return manufacturerName
   */
  @Setter
  @Getter
  private String manufacturerName;

  public CarManufacturer(String manufacturerName) {
    this.manufacturerName = manufacturerName;
  }
}
