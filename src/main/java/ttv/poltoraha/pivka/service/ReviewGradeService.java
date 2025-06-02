package ttv.poltoraha.pivka.service;

import ttv.poltoraha.pivka.dao.request.ReviewRequestDto;
import ttv.poltoraha.pivka.entity.ReviewGrade;

public interface ReviewGradeService {
    public ReviewGrade addGradeToReview(ReviewRequestDto request);
    public void updateReviewRating(Integer reviewId);
}
