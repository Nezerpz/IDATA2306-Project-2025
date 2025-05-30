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
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.ntnu.rentalroulette.enums.OrderStatus;

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
  private LocalDate dateFrom;

  @Setter
  @Getter
  private LocalDate dateTo;

  @Getter
  @Setter
  private LocalTime timeFrom;

  @Getter
  @Setter
  private LocalTime timeTo;

  @Getter
  @Setter
  private String pricePaid;

  @Setter
  @Getter
  private OrderStatus orderStatus;

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
  private Car car;

  public Order(
      User customer,
      User provider,
      LocalDate startDate,
      LocalDate endDate,
      LocalTime startTime,
      LocalTime endTime,
      String pricePaid,
      Car car,
      OrderStatus orderStatus
  ) {
    this.customer = customer;
    this.provider = provider;
    this.dateFrom = startDate;
    this.dateTo = endDate;
    this.timeFrom = startTime;
    this.timeTo = endTime;
    this.pricePaid = pricePaid;
    this.car = car;
    this.orderStatus = orderStatus;
  }

  @JsonProperty("customerId")
  public int getCustomerId() {
    if (customer == null) {
      return -1;
    }
    return customer.getId();
  }

  @JsonProperty("providerId")
  public int getProviderId() {
    if (provider == null) {
      return -1;
    }
    return provider.getId();
  }

}
