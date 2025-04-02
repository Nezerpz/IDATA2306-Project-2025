package no.ntnu.rentalroulette.repository;

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

  @Query("SELECT c FROM Car c WHERE not c.id IN ( SELECT o.car.id FROM Order o WHERE o.endDate > :startDate AND o.startDate < :endDate AND o.endTime > :endTime AND o.startTime < :startTime) ")
  List<Car> findAvailableCars(
          @Param("startDate") String startDate,
          @Param("endDate") String endDate,
          @Param("startTime") String startTime,
          @Param("endTime") String endTime
  );

}
