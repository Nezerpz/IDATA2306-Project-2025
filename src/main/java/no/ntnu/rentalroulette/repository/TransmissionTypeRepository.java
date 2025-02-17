package no.ntnu.rentalroulette.repository;

import no.ntnu.rentalroulette.entity.TransmissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransmissionTypeRepository extends JpaRepository<TransmissionType, Integer> {
}
