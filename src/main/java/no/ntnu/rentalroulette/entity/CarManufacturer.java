package no.ntnu.rentalroulette.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
  private String name;

  @Getter
  @OneToMany(mappedBy = "manufacturer")
  private Set<Car> cars;

  public CarManufacturer(String manufacturerName) {
    this.name = manufacturerName;
  }
}
