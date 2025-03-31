package no.ntnu.rentalroulette.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"ORDER\"")
public class Order {
  @Id
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Setter
  @Getter
  private LocalDate startDate;

  @Setter
  @Getter
  private LocalDate endDate;

  @Setter
  @Getter
  private String pricePaid;

  @Setter
  @Getter
  private boolean orderStatus;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  @JsonProperty("customerId")
  private User customer;

  @ManyToOne
  @JoinColumn(name = "provider_id")
  @JsonProperty("providerId")
  private User provider;

  @ManyToOne
  @JoinColumn(name = "car_id")
  @JsonProperty("carId")
  private Car car;

  public Order(User customer, User provider, LocalDate startDate, LocalDate endDate,
               String pricePaid, Car car, boolean orderStatus) {
    this.customer = customer;
    this.provider = provider;
    this.startDate = startDate;
    this.endDate = endDate;
    this.pricePaid = pricePaid;
    this.car = car;
    this.orderStatus = orderStatus;
  }

  @JsonProperty("customerId")
  public int getCustomerId() {
    return customer.getId();
  }

  @JsonProperty("providerId")
  public int getProviderId() {
    return provider.getId();
  }

  @JsonProperty("carId")
  public int getCarId() {
    return car.getId();
  }
}
