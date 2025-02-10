package no.ntnu.rentalroulette;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CarToProvider {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "car_id")
  private Car car;

  @ManyToOne
  @JoinColumn(name = "provider_id")
  private Provider provider;

  private int stock;
  private double price;

  public CarToProvider() {
  }

  public CarToProvider(Car car, Provider provider, int stock, double price) {
    this.car = car;
    this.provider = provider;
    this.stock = stock;
    this.price = price;
  }

  /**
   * Get Entity's Car.
   *
   * @return car
   */
  public Car getCar() {
    return car;
  }

  /**
   * Get Entity's Provider.
   *
   * @return provider
   */
  public Provider getProvider() {
    return provider;
  }

  /**
   * Get Entity's stock.
   *
   * @return stock
   */
  public int getStock() {
    return stock;
  }

  /**
   * Get Entity's price.
   *
   * @return price
   */
  public double getPrice() {
    return price;
  }
}
