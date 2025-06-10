package ttv.poltoraha.pivka.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.dao.request.ReviewGradeRequestDto;
import ttv.poltoraha.pivka.entity.Review;
import ttv.poltoraha.pivka.entity.ReviewGrade;
import ttv.poltoraha.pivka.entity.Reader;
import ttv.poltoraha.pivka.repository.ReviewGradeRepository;
import ttv.poltoraha.pivka.repository.ReviewRepository;
import ttv.poltoraha.pivka.repository.ReaderRepository;
import ttv.poltoraha.pivka.service.ReviewGradeService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewGradeServiceImpl implements ReviewGradeService {

    private final ReviewRepository reviewRepository;
    private final ReviewGradeRepository reviewGradeRepository;
    private final ReaderRepository readerRepository;

    public void addGradeToReview(ReviewGradeRequestDto request) {
        Review review = reviewRepository.findById(request.getReviewId())
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        Reader reader = readerRepository.findById(request.getReaderUsername())
                .orElseThrow(() -> new EntityNotFoundException("Reader not found"));

        if (request.getGrade() < 1 || request.getGrade() > 5) {
            throw new IllegalArgumentException("Grade value must be between 1 and 5");
        }

        Optional<ReviewGrade> existingGrade = reviewGradeRepository
                .findByReviewIdAndReaderUsername(request.getReviewId(), request.getReaderUsername());

        ReviewGrade grade;
        if (existingGrade.isPresent()) {
            grade = existingGrade.get();
            grade.setGrade(request.getGrade());
        } else {
            grade = new ReviewGrade();
            grade.setReview(review);
            grade.setReaderUsername(reader.getUsername());
            grade.setGrade(request.getGrade());
        }

        reviewGradeRepository.save(grade);

        updateReviewRating(review.getId(), grade);

    }

    public void updateReviewRating(Integer reviewId, ReviewGrade reviewGrade) {
//        Double averageRating = ReviewGradeRepository.findAverageGradeByReviewId(ReviewId);
//        ReviewRepository.findById(ReviewId).ifPresent(Review -> {
//            Review.setAvgrating(averageRating);
//            ReviewRepository.save(Review);
//        });
        val review = reviewRepository.findById(reviewId);
        val allGrades = review.get().getGrades().stream()
                .mapToDouble(ReviewGrade::getGrade)
                .sum();
        val newReviewRating = (allGrades + reviewGrade.getGrade()) / (review.get().getGrades().size() + 1);
        review.get().setRating(newReviewRating);
        reviewRepository.save(review.get());
    }
}