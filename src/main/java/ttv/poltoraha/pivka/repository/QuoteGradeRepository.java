package ttv.poltoraha.pivka.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ttv.poltoraha.pivka.entity.QuoteGrade;

import java.util.List;
import java.util.Optional;


@Repository
public interface QuoteGradeRepository extends JpaRepository<QuoteGrade, Integer> {

    Optional<QuoteGrade> findByQuoteIdAndReaderUsername(Integer quoteId, String readerUsername);

    @Query("SELECT qg FROM quoteGrades qg WHERE qg.quote.id = :quoteId")
    List<QuoteGrade> findAllGradesByQuoteId(@Param("quoteId") Integer quoteId);

    @Query("SELECT AVG(qg.value) FROM quoteGrades qg WHERE qg.quote.id = :quoteId")
    Double findAverageGradeByQuoteId(@Param("quoteId") Integer quoteId);

}

