package ttv.poltoraha.pivka.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ttv.poltoraha.pivka.entity.QuoteGrade;


import java.util.Optional;


@Repository
public interface QuoteGradeRepository extends JpaRepository<QuoteGrade, Integer> {
    Optional<QuoteGrade> findByQuoteIdAndReaderUsername(Integer quoteId, String readerUsername);
}