package no.ntnu.rentalroulette.repository;

import no.ntnu.rentalroulette.entity.Car;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
  Set<Car> findByManufacturer_Name(String name);

  Car findByCarModel(String carModel);
  Car findById(int id);
  Set<String> findDistinctByCarModel(String carModel);
}
