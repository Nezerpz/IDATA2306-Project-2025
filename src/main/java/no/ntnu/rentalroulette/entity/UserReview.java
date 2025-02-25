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
@Table(name = "user_review")
public class UserReview {

  @Id
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Setter
  @Getter
  @ManyToOne
  private User reviewed_user_id;

  @Setter
  @Getter
  @ManyToOne
  private User reviewing_user_id;

  @Setter
  @Getter
  private int rating;

  @Setter
  @Getter
  private String review;

  public UserReview(User reviewed_user_id, User reviewing_user_id, int rating, String review) {
    this.reviewed_user_id = reviewed_user_id;
    this.reviewing_user_id = reviewing_user_id;
    this.rating = rating;
    this.review = review;
  }
}
