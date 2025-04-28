package no.ntnu.rentalroulette.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "user_review")
public class UserReview {

  @Id
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Setter
  @Getter
  @ManyToOne
  private User reviewedUser;

  @Setter
  @Getter
  @ManyToOne
  private User reviewingUser;

  @Setter
  @Getter
  private int rating;

  @Setter
  @Getter
  private String review;

  public UserReview(User reviewedUser, User reviewingUser, int rating, String review) {
    this.reviewedUser = reviewedUser;
    this.reviewingUser = reviewingUser;
    this.rating = rating;
    this.review = review;
  }
}
