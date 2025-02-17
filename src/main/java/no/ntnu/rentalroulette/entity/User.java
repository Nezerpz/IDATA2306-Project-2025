package no.ntnu.rentalroulette.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "\"user\"")
public class User {
  @Id
  /**
   * Entity's row ID in Database.
   *
   * @return id
   */
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  /**
   * Entity's UserType.
   *
   * @param userType
   * @return userType
   */
  @Setter
  @Getter
  @ManyToOne
  @JoinColumn(name = "user_type_id", referencedColumnName = "id")
  private UserType userType;
  /**
   * Entity's first name.
   *
   * @param firstName
   * @return firstName
   */
  @Setter
  @Getter
  private String firstName;
  /**
   * Entity's last name.
   *
   * @param lastName
   * @return lastName
   */
  @Setter
  @Getter
  private String lastName;
  /**
   * Entity's username.
   *
   * @param username
   * @return username
   */
  @Setter
  @Getter
  private String username;
  /**
   * Entity's password.
   *
   * @param password
   * @return password
   */
  @Setter
  @Getter
  @Column(name = "\"password\"")
  private String password;
  /**
   * Entity's email.
   *
   * @param email
   * @return email
   */
  @Setter
  @Getter
  private String email;

  public User(UserType userType, String firstName, String lastName, String username,
              String password, String email) {
    this.userType = userType;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.email = email;
  }
}
