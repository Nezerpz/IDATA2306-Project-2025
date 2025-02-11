package no.ntnu.rentalroulette.repository;

import no.ntnu.rentalroulette.entity.CarManufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarManufacturerRepository extends JpaRepository<CarManufacturer, Integer> {
    CarManufacturer findByName(String name);

}
