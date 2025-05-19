package ttv.poltoraha.pivka.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.dao.request.AuthorDto;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.mapping.MappingUtil;
import ttv.poltoraha.pivka.repository.AuthorRepository;
import ttv.poltoraha.pivka.service.AuthorService;

import java.util.List;

// Имплементации интерфейсов с бизнес-логикой
@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);

    // todo как будто надо насрать всякими мапперами
    @Override
    public void create(AuthorDto author) {
        String name = author.getFullName();
        logger.info("добавляем автора: {}", name);
        Author mappedauthor = MappingUtil.AuthorFromDto(author);
        authorRepository.save(mappedauthor);
        logger.info("добавили автора: {}", name);
    }

    @Override
    public void delete(Integer id) {
        if (!authorRepository.existsById(id)) {
            throw new EntityNotFoundException("Author with id = " + id + " not found");
        }
        logger.info("удаляем автора с ID: {}", id);
        authorRepository.deleteById(id);
        logger.info("удалили автора с ID: {}", id);
    }

    @Override
    public void addBooks(Integer id, List<Book> books) {
        val author = getOrThrow(id);
        logger.info("добавляем книги автору с ID: {}", id);
        author.getBooks().addAll(books);
        logger.info("добавили книги автору с ID: {}", id);
    }

    @Override
    public void addBook(Integer id, Book book) {
        val author = getOrThrow(id);
        logger.info("добавляем книгу автору с ID: {}", id);
        author.getBooks().add(book);
        logger.info("добавили книгу автору с ID: {}", id);
    }

    @Override
    public List<Author> getTopAuthorsByTag(String tag, int count) {
        Pageable pageable = PageRequest.of(0, count);
        logger.info("ищем топ авторов по тэгу: {} ", tag);
        val authors = authorRepository.findTopAuthorsByTag(tag);
        logger.info("нашли топ авторов по тэгу: {} ", tag);
        return authorRepository.findTopAuthorsByTag(tag, pageable);

    }

    private Author getOrThrow(Integer id) {
        logger.info("ищем автора по айди: {} ", id);
        val optionalAuthor = authorRepository.findById(id);
        logger.info("нашли автора по айди: {} ", id);
        val author = optionalAuthor.orElse(null);

        logger.info("проверка автора с ID: {} на null", id);
        if (author == null) {
            throw new EntityNotFoundException("Author with id = " + id + " not found");
        }
        logger.info("проверка автора с ID: {} прошла успешно", id);
        return author;
    }
}
