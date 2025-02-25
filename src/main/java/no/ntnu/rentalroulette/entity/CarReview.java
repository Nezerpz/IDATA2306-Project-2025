package no.ntnu.rentalroulette.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Table(name = "car_review")
public class CarReview {

  @Id
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Setter
  @Getter
  @ManyToOne
  private User user;

  @Setter
  @Getter
  @ManyToOne
  private Car car;

  @Setter
  @Getter
  private int rating;

  @Setter
  @Getter
  private String review;

  public CarReview(User user, Car car, int rating, String review) {
    this.user = user;
    this.car = car;
    this.rating = rating;
    this.review = review;
  }
}
