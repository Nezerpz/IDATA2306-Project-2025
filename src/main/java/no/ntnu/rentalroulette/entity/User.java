package no.ntnu.rentalroulette.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"user\"")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @OneToOne
  @JoinColumn(name = "user_type_id", referencedColumnName = "id")
  private UserType userType;
  private String firstName;
  private String lastName;
  private String username;
  @Column(name = "\"password\"")
  private String password;
  private String email;

  public User() {
  }

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
