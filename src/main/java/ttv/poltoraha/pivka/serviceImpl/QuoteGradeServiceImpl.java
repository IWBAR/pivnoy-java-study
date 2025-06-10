package ttv.poltoraha.pivka.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.dao.request.QuoteGradeRequestDto;
import ttv.poltoraha.pivka.entity.Quote;
import ttv.poltoraha.pivka.entity.QuoteGrade;
import ttv.poltoraha.pivka.entity.Reader;
import ttv.poltoraha.pivka.repository.QuoteGradeRepository;
import ttv.poltoraha.pivka.repository.QuoteRepository;
import ttv.poltoraha.pivka.repository.ReaderRepository;
import ttv.poltoraha.pivka.service.QuoteGradeService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuoteGradeServiceImpl implements QuoteGradeService {

    private final QuoteRepository quoteRepository;
    private final QuoteGradeRepository quoteGradeRepository;
    private final ReaderRepository readerRepository;

    public void addGradeToQuote(QuoteGradeRequestDto request) {
        Quote quote = quoteRepository.findById(request.getQuoteId())
                .orElseThrow(() -> new EntityNotFoundException("Quote not found"));

        Reader reader = readerRepository.findById(request.getReaderUsername())
                .orElseThrow(() -> new EntityNotFoundException("Reader not found"));

        if (request.getGrade() < 1 || request.getGrade() > 5) {
            throw new IllegalArgumentException("Grade value must be between 1 and 5");
        }

        Optional<QuoteGrade> existingGrade = quoteGradeRepository
                .findByQuoteIdAndReaderUsername(request.getQuoteId(), request.getReaderUsername());

        QuoteGrade grade;
        if (existingGrade.isPresent()) {
            grade = existingGrade.get();
            grade.setGrade(request.getGrade());
        } else {
            grade = new QuoteGrade();
            grade.setQuote(quote);
            grade.setReader(reader);
            grade.setGrade(request.getGrade());
        }

        quoteGradeRepository.save(grade);

        updateQuoteRating(quote.getId(), grade);

    }

    public void updateQuoteRating(Integer quoteId, QuoteGrade quoteGrade) {
//        Double averageRating = quoteGradeRepository.findAverageGradeByQuoteId(quoteId);
//        quoteRepository.findById(quoteId).ifPresent(quote -> {
//            quote.setAvgrating(averageRating);
//            quoteRepository.save(quote);
//        });
        val quote = quoteRepository.findById(quoteId);
        val allGrades = quote.get().getGrades().stream()
                .mapToDouble(QuoteGrade::getGrade)
                .sum();
        val newQuoteRating = (allGrades + quoteGrade.getGrade()) / (quote.get().getGrades().size() + 1);
        quote.get().setRating(newQuoteRating);
        quote.ifPresent(quoteRepository::save);
    }
}