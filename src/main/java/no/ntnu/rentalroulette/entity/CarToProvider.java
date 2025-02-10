package no.ntnu.rentalroulette.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "car_to_provider")
public class CarToProvider {
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
   * Entity's car.
   *
   * @param car
   * @return car
   */
  @Setter
  @Getter
  @ManyToOne
  @JoinColumn(name = "car_id")
  private Car car;

  /**
   * Entity's provider.
   *
   * @param provider
   * @return provider
   */
  @Setter
  @Getter
  @ManyToOne
  @JoinColumn(name = "provider_id")
  private Provider provider;

  /**
   * Entity's stock.
   *
   * @param stock
   * @return stock
   */
  @Setter
  @Getter
  private int stock;
  /**
   * Entity's price.
   *
   * @param price
   * @return price
   */
  @Setter
  @Getter
  private double price;

  public CarToProvider(Car car, Provider provider, int stock, double price) {
    this.car = car;
    this.provider = provider;
    this.stock = stock;
    this.price = price;
  }
}
