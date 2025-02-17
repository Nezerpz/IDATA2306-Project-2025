package no.ntnu.rentalroulette.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "\"order\"")
public class Order {
  @Id
  /**
   * Entity's row ID in Database.
   *
   * @return id
   */
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  @JoinColumn(name = "car_to_provider_id")
  private CarToProvider carToProvider;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  /**
   * Entity's start date.
   *
   * @param startDate
   * @return startDate
   */
  @Setter
  @Getter
  private LocalDate startDate;
  /**
   * Entity's end date.
   *
   * @param endDate
   * @return endDate
   */
  @Setter
  @Getter
  private LocalDate endDate;
  /**
   * Entity's price paid.
   *
   * @param pricePaid
   * @return pricePaid
   */
  @Setter
  @Getter
  private String pricePaid;

  public Order(CarToProvider carToProvider, User user, LocalDate startDate, LocalDate endDate,
               String pricePaid) {
    this.carToProvider = carToProvider;
    this.user = user;
    this.startDate = startDate;
    this.endDate = endDate;
    this.pricePaid = pricePaid;
  }
}
