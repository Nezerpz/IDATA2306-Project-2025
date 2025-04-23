package no.ntnu.rentalroulette.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import no.ntnu.rentalroulette.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "\"USER\"")
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
  @Enumerated(EnumType.STRING)
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
  @JsonIgnore
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

  /**
   * Entity's telephone number.
   */
  @Setter
  @Getter
  private String telephoneNumber;

  @Getter
  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private List<Car> cars;


  @Getter
  @Setter
  private boolean active;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles = new LinkedHashSet<Role>();

  public User(UserType userType, String firstName, String lastName, String username,
              String password, String email, String telephoneNumber) {
    this.userType = userType;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.email = email;
    this.telephoneNumber = telephoneNumber;
    this.active = true;
  }

}
