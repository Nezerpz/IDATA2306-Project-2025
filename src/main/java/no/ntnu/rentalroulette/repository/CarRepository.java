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

  // I am sorry...
  // BUT JPQL DOES NOT HAVE IF-STATEMENTS!!!!!
  @Query("""
        SELECT c FROM Car c WHERE not c.id IN (
            SELECT o.car.id FROM Order o
            WHERE (
               (o.dateFrom = :dateFrom  AND o.timeFrom   <= :timeFrom) OR  
               (o.dateFrom = :dateFrom  AND o.timeTo     >= :timeTo)   OR  

               (o.dateTo   = :dateTo    AND o.timeFrom   <= :timeFrom) OR  
               (o.dateTo   = :dateTo    AND o.timeTo     >= :timeTo)   OR 

               (o.dateFrom < :dateFrom  AND o.dateTo     >  :dateTo)
            )
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


  //TODO: Slim down query by excluding User as default when updating.
  @Query("""
            UPDATE Car c 
            SET c.imagePath = :#{#car.imagePath}, 
            c.manufacturer = :#{#car.manufacturer}, 
            c.carModel = :#{#car.carModel}, 
            c.price = :#{#car.price}, 
            c.numberOfSeats = :#{#car.numberOfSeats}, 
            c.transmissionType = :#{#car.transmissionType}, 
            c.fuelType = :#{#car.fuelType}, 
            c.productionYear = :#{#car.productionYear}, 
            c.carStatus = :#{#car.carStatus}, 
            c.features = :#{#car.features} 
            WHERE c.id = :id
      """)
  void updateCarById(@Param("id") int id, @Param("car") Car car);

}
