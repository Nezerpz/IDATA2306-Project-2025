package no.ntnu.rentalroulette.repository;

import java.util.List;
import no.ntnu.rentalroulette.entity.User;
import no.ntnu.rentalroulette.entity.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReviewRepository extends JpaRepository<UserReview, Integer> {

  boolean existsByReviewedUserAndReviewingUser(User reviewedUser,
                                               User reviewingUser);

  void deleteAllByReviewedUserId(int userId);

  void deleteAllByReviewingUserId(int userId);

  List<UserReview> findAllByReviewedUser(User reviewedUser);
  List<UserReview> findAllByReviewingUser(User reviewingUser);
}
