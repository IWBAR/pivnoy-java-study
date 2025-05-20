package ttv.poltoraha.pivka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.entity.Reader;
import ttv.poltoraha.pivka.service.RecommendationService;

@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/recommendAuthor")
    public void getRecommendAuthor(Reader reader){
        recommendationService.recommendAuthor(reader.getUsername());
    }

    @PostMapping("/recommendedBooks")
    public void getRecommendBooks(Reader reader){
        recommendationService.recommendBook(reader.getUsername());
    }

    @PostMapping("/recommendedBooks")
    public void getRecommendQuoteByBook(Book book){
        recommendationService.recommendQuoteByBook(book.getId());
    }
}
