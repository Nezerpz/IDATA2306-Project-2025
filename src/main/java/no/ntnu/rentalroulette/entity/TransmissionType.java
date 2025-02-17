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
@Table(name = "transmission_type")
public class TransmissionType {
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
   * Entity user type.
   *
   * @param transmissionType
   * @return transmissionType
   */
  @Setter
  @Getter
  private String transmissionType;

  public TransmissionType(String transmissionType) {
    this.transmissionType = transmissionType;
  }

}
