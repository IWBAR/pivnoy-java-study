package ttv.poltoraha.pivka.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ttv.poltoraha.pivka.entity.Book;

import java.util.List;
@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

    @Query(value = "SELECT b.* FROM books b " +
            "JOIN authors a ON b.author_id = a.id " +
            "WHERE CONCAT(' ', a.full_name, ' ') LIKE CONCAT('% ', :lastName, ' %') " +
            "ORDER BY a.avg_rating DESC, a.id ASC",
            nativeQuery = true)
    List<Book> findAllBooksByAuthorLastName(@Param("lastName") String lastName);
}
