package no.ntnu.rentalroulette;

import jakarta.persistence.Column;
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
  @Column(name = "user_type")
  private String userType;
}
