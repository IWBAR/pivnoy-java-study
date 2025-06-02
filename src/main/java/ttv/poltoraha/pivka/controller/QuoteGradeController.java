package ttv.poltoraha.pivka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ttv.poltoraha.pivka.dao.request.QuoteGradeRequestDto;
import ttv.poltoraha.pivka.entity.QuoteGrade;
import ttv.poltoraha.pivka.service.QuoteGradeService;

@RestController
@RequestMapping("/quotes")
@RequiredArgsConstructor
public class QuoteGradeController {
    private final QuoteGradeService quoteGradeService;

    @PostMapping("/grade")
    public ResponseEntity<QuoteGrade> addGrade(@RequestBody QuoteGradeRequestDto request) {
        QuoteGrade grade = quoteGradeService.addGradeToQuote(request);
        return ResponseEntity.ok(grade);
    }
}
