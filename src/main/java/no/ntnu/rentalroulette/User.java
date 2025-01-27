package no.ntnu.rentalroulette;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @OneToOne
  @JoinColumn(name = "user_type_id", referencedColumnName = "id")
  private UserType userTypeId;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "username")
  private String username;
  @Column(name = "password")
  private String password;
  @Column(name = "email")
  private String email;
}
