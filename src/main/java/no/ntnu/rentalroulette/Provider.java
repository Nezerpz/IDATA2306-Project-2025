package no.ntnu.rentalroulette;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Provider {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String address;
  private String email;

  public Provider() {
  }

  public Provider(String name, String address, String email) {
    this.name = name;
    this.address = address;
    this.email = email;
  }

  /**
   * Get Entity's row ID in Database.
   *
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * Get Entity name.
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Get Entity address.
   *
   * @return address
   */
  public String getAddress() {
    return address;
  }

  /**
   * Get Entity email.
   *
   * @return email
   */
  public String getEmail() {
    return email;
  }
}
