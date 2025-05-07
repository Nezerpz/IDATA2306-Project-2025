package no.ntnu.rentalroulette.repository;

import java.util.List;
import no.ntnu.rentalroulette.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

  @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId")
  List<Order> findAllByCustomerId(@Param("customerId") int customerId);

  @Query("SELECT o FROM Order o WHERE o.provider.id = :providerId")
  List<Order> findAllByProviderId(@Param("providerId") int providerId);

  @Query("SELECT o FROM Order o WHERE o.car.id = :carId")
  List<Order> findAllByCarId(@Param("carId") int carId);
}