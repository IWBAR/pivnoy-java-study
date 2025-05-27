package ttv.poltoraha.pivka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public List<Author> getRecommendAuthor(@RequestBody Reader reader){
        return recommendationService.recommendAuthor(reader.getUsername());
    }

    @PostMapping("/recommendedBooks")
    public List<Book> getRecommendBooks(@RequestBody Reader reader){
        return recommendationService.recommendBook(reader.getUsername());
    }

    @PostMapping("/recommendedQuote")
    public List<Quote> getRecommendQuoteByBook(@RequestBody Book book){
        return recommendationService.recommendQuoteByBook(book.getId());
    }
}
