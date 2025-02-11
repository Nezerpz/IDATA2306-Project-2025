package no.ntnu.rentalroulette.entity;

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
@Table(name = "provider")
public class Provider {
  /**
   * Entity's row ID in Database.
   *
   * @return id
   */
  @Id
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;


  /**
   * Entity's name.
   *
   * @param name
   * @return name
   */
  @Setter
  @Getter
  private String name;


  /**
   * Entity's address.
   *
   * @param address
   * @return address
   */
  @Setter
  @Getter
  private String address;


  /**
   * Entity's email.
   *
   * @param email
   * @return email
   */
  @Setter
  @Getter
  private String email;

  public Provider(String name, String address, String email) {
    this.name = name;
    this.address = address;
    this.email = email;
  }
}
