package no.ntnu.rentalroulette.repository;

import no.ntnu.rentalroulette.entity.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReviewRepository extends JpaRepository<UserReview, Integer> {
}
