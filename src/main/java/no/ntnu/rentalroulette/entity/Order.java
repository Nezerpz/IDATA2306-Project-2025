package no.ntnu.rentalroulette.entity;

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

  @Getter
  @Setter
  private LocalTime startTime;

  @Getter
  @Setter
  private LocalTime endTime;

  @Getter
  @Setter
  private String pricePaid;

  @Setter
  @Getter
  private boolean orderStatus;

  @Getter
  @ManyToOne
  @JoinColumn(name = "customer_id")
  private User customer;

  @Getter
  @ManyToOne
  @JoinColumn(name = "provider_id")
  private User provider;

  @Getter
  @ManyToOne
  @JoinColumn(name = "car_id")
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
}
