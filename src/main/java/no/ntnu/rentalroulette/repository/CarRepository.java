package no.ntnu.rentalroulette.repository;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import no.ntnu.rentalroulette.entity.Car;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

  List<Car> findByCarModel(String carModel);

  Car findById(int id);

  Set<String> findDistinctByCarModel(String carModel);

  @Query("SELECT c FROM Car c WHERE c.user.id = :providerId")
  List<Car> findAllByProviderId(@Param("providerId") int providerId);

  @Query("SELECT c FROM Car c JOIN Order o ON c.id = o.car.id WHERE o.id = :orderId")
  Car findByOrderId(@Param("orderId") int orderId);
}
