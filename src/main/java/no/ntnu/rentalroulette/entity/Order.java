package no.ntnu.rentalroulette.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
  @JoinColumn(name = "car_id")
  private CarToProvider car;
  @ManyToOne
  @JoinColumn(name = "provider_id")
  private CarToProvider provider;
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
  private String startDate;
  /**
   * Entity's end date.
   *
   * @param endDate
   * @return endDate
   */
  @Setter
  @Getter
  private String endDate;
  /**
   * Entity's price paid.
   *
   * @param pricePaid
   * @return pricePaid
   */
  @Setter
  @Getter
  private String pricePaid;
}
