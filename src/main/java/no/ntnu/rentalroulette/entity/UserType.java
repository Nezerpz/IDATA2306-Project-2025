package no.ntnu.rentalroulette.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_type")
public class UserType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String userType;

  public UserType() {
  }

  public UserType(String userType) {
    this.userType = userType;
  }

  public int getId() {
    return this.id;
  }
}
