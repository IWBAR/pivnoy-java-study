package ttv.poltoraha.pivka.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.dao.request.ReviewGradeRequestDto;
import ttv.poltoraha.pivka.entity.Reader;
import ttv.poltoraha.pivka.entity.Review;
import ttv.poltoraha.pivka.entity.ReviewGrade;
import ttv.poltoraha.pivka.repository.ReaderRepository;
import ttv.poltoraha.pivka.repository.ReviewGradeRepository;
import ttv.poltoraha.pivka.repository.ReviewRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewGradeServiceImpl {
    private final ReviewRepository reviewRepository;
    private final ReviewGradeRepository reviewGradeRepository;
    private final ReaderRepository readerRepository;

    public ReviewGrade addGradeToReview(ReviewGradeRequestDto request) {
        Review review = reviewRepository.findById(request.getReviewID())
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        Reader reader = readerRepository.findById(request.getReaderUsername())
                .orElseThrow(() -> new EntityNotFoundException("Reader not found"));

        if (request.getValue() < 1 || request.getValue() > 5) {
            throw new IllegalArgumentException("Grade value must be between 1 and 5");
        }

        Optional<ReviewGrade> existingGrade = reviewGradeRepository
                .findByReviewIdAndReaderUsername(request.getReviewID(), request.getReaderUsername());

        ReviewGrade grade;
        if (existingGrade.isPresent()) {
            grade = existingGrade.get();
            grade.setValue(request.getValue());
        } else {
            grade = new ReviewGrade();
            grade.setReview(review);
            grade.setReader(reader);
            grade.setValue(request.getValue());
        }

        ReviewGrade savedGrade = reviewGradeRepository.save(grade);

        updateReviewRating(review.getId());

        return savedGrade;
    }

    public void updateReviewRating(Integer reviewId) {
        Double averageRating = reviewGradeRepository.findAverageGradeByReviewId(reviewId);
        reviewRepository.findById(reviewId).ifPresent(review -> {
            review.setRating(averageRating);
            reviewRepository.save(review);
        });
    }
}
