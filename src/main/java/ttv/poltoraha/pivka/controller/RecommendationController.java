package ttv.poltoraha.pivka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.entity.Quote;
import ttv.poltoraha.pivka.entity.Reader;
import ttv.poltoraha.pivka.service.RecommendationService;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/recommendAuthor")
    public List<Author> getRecommendAuthor(Reader reader){
        return recommendationService.recommendAuthor(reader.getUsername());
    }

    @PostMapping("/recommendedBooks")
    public List<Book> getRecommendBooks(Reader reader){
        return recommendationService.recommendBook(reader.getUsername());
    }

    @PostMapping("/recommendedBooks")
    public List<Quote> getRecommendQuoteByBook(Book book){
        return recommendationService.recommendQuoteByBook(book.getId());
    }
}
