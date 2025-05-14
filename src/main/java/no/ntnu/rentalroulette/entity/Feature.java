package no.ntnu.rentalroulette.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feature")
public class Feature {
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
   * Entity's feature name.
   *
   * @param featureName
   * @return featureName
   */
  @Setter
  @Getter
  private String featureName;

  /**
   * Entity's feature description.
   *
   * @param description
   * @return description
   */
  @Setter
  @Getter
  private String description;

  @JsonIgnore
  @ManyToMany(mappedBy = "features")
  private List<Car> cars;

  public Feature(String featureName) {
    this.featureName = featureName;
    this.description = null;
  }

  public Feature(String featureName, String description) {
    this.featureName = featureName;
    this.description = description;
  }

}
