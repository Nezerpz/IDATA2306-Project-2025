package no.ntnu.rentalroulette.repository;

import no.ntnu.rentalroulette.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer> {
  Feature findByFeatureName(String featureName);
  List<Feature> findAll();
}
