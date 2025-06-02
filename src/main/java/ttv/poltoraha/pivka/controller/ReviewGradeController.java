package ttv.poltoraha.pivka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ttv.poltoraha.pivka.dao.request.QuoteGradeRequestDto;
import ttv.poltoraha.pivka.dao.request.ReviewGradeRequestDto;
import ttv.poltoraha.pivka.entity.QuoteGrade;

import ttv.poltoraha.pivka.entity.ReviewGrade;
import ttv.poltoraha.pivka.service.ReviewGradeService;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewGradeController {
    private final ReviewGradeService reviewGradeService;

    @PostMapping("/grade")
    public ResponseEntity<ReviewGrade> addGrade(@RequestBody ReviewGradeRequestDto request) {
        ReviewGrade grade = reviewGradeService.addGradeToReview(request);
        return ResponseEntity.ok(grade);
    }
}
