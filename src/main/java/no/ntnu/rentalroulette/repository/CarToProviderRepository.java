package no.ntnu.rentalroulette.repository;

import no.ntnu.rentalroulette.entity.CarToProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarToProviderRepository extends JpaRepository<CarToProvider, Integer> {
}