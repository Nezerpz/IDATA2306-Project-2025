package no.ntnu.rentalroulette;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "car_manufacturer")
public class CarManufacturer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String manufacturerName;

    public CarManufacturer() {
    }

    public CarManufacturer(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
}
