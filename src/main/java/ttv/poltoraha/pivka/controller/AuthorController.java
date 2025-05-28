package ttv.poltoraha.pivka.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ttv.poltoraha.pivka.dao.request.AuthorDto;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.metrics.CustomMetrics;
import ttv.poltoraha.pivka.repository.AuthorRepository;
import ttv.poltoraha.pivka.service.AuthorService;

import java.util.List;

// Контроллеры - это классы для создания внешних http ручек. Чтобы к нам могли прийти по http, например, через постман
// Или если у приложухи есть веб-морда, каждое действие пользователя - это http запросы
@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorRepository authorRepository;
    private final Logger logger = LoggerFactory.getLogger(AuthorController.class);
    private final CustomMetrics metrics;

    @PostMapping("/create")
    public ResponseEntity<Void> createAuthor(@RequestBody AuthorDto author) {
        metrics.recordCounter();
        String name = author.getFullName();
        logger.info("создаем автора: {}", name);
        StopWatch timer = new StopWatch();
        timer.start();
        authorService.create(author);
        timer.stop();
        metrics.recordTimer(timer.getNanoTime());
        logger.info("создали автора: {}", name);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteAuthorById(@RequestParam Integer id) {
        metrics.recordCounter();
        logger.info("удаляем автора с ID: {}", id);
        StopWatch timer = new StopWatch();
        timer.start();
        authorService.delete(id);
        timer.stop();
        metrics.recordTimer(timer.getNanoTime());
        logger.info("удалили автора с ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add/books")
    public ResponseEntity<Void> addBooksToAuthor(@RequestParam Integer id, @RequestBody List<Book> books) {
        metrics.recordCounter();
        logger.info("добавляем книги автору с ID: {}", id);
        StopWatch timer = new StopWatch();
        timer.start();
        authorService.addBooks(id, books);
        timer.stop();
        metrics.recordTimer(timer.getNanoTime());
        logger.info("добавили книги автору с ID: {}", id);
        return ResponseEntity.ok().build();
    }
}
