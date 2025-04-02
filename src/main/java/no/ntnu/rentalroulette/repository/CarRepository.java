package no.ntnu.rentalroulette.repository;

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

  @Query("""
    SELECT c FROM Car c WHERE not c.id IN (
        SELECT o.car.id FROM Order o
        WHERE o.dateFrom <= :dateFrom
        AND   o.dateTo   >= :dateTo
        AND   o.timeFrom <= :timeFrom
        AND   o.timeTo   >= :timeTo
    )
  """)
  List<Car> findAvailableCars(
          @Param("dateFrom") LocalDate dateFrom,
          @Param("dateTo") LocalDate dateTo,
          @Param("timeFrom") LocalTime timeFrom,
          @Param("timeTo") LocalTime timeTo
  );

  @Query("SELECT c FROM Car c WHERE c.user.id = :providerId")
  List<Car> findAllByProviderId(@Param("providerId") int providerId);
}
