package ttv.poltoraha.pivka.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ttv.poltoraha.pivka.entity.ReviewGrade;


import java.util.Optional;


@Repository
public interface ReviewGradeRepository extends JpaRepository<ReviewGrade, Integer> {
    Optional<ReviewGrade> findByReviewIdAndReaderUsername(Integer reviewId, String readerUsername);
}