package ttv.poltoraha.pivka.service;

import ttv.poltoraha.pivka.dao.request.ReviewGradeRequestDto;
import ttv.poltoraha.pivka.entity.ReviewGrade;

public interface ReviewGradeService {
    public ReviewGrade addGradeToReview(ReviewGradeRequestDto request);
    public void updateReviewRating(Integer reviewId);
}
