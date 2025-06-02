package ttv.poltoraha.pivka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ttv.poltoraha.pivka.entity.QuoteGrade;
import ttv.poltoraha.pivka.entity.ReviewGrade;

import java.util.List;
import java.util.Optional;

public interface ReviewGradeRepository extends JpaRepository<ReviewGrade, Integer> {

    Optional<ReviewGrade> findByReviewIdAndReaderUsername(Integer reviewId, String readerUsername);

    @Query("SELECT rg FROM reviewGrades rg WHERE rg.review.id = :reviewId")
    List<QuoteGrade> findAllGradesByReviewId(@Param("reviewId") Integer reviewId);

    @Query("SELECT AVG(rg.value) FROM reviewGrades rg WHERE rg.review.id = :reviewId")
    Double findAverageGradeByReviewId(@Param("reviewId") Integer reviewId);

}
