package no.ntnu.rentalroulette.repository;

import java.util.List;
import no.ntnu.rentalroulette.entity.Car;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

  List<Car> findByCarModel(String carModel);

  Car findById(int id);

  Set<String> findDistinctByCarModel(String carModel);

}
