package no.ntnu.rentalroulette.repository;

import no.ntnu.rentalroulette.entity.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Integer> {
}
