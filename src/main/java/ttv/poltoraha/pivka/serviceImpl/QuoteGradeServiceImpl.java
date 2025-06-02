package ttv.poltoraha.pivka.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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

    public QuoteGrade addGradeToQuote(QuoteGradeRequestDto request) {
        Quote quote = quoteRepository.findById(request.getQuoteId())
                .orElseThrow(() -> new EntityNotFoundException("Quote not found"));

        Reader reader = readerRepository.findById(request.getReaderUsername())
                .orElseThrow(() -> new EntityNotFoundException("Reader not found"));

        if (request.getValue() < 1 || request.getValue() > 5) {
            throw new IllegalArgumentException("Grade value must be between 1 and 5");
        }

        Optional<QuoteGrade> existingGrade = quoteGradeRepository
                .findByQuoteIdAndReaderUsername(request.getQuoteId(), request.getReaderUsername());

        QuoteGrade grade;
        if (existingGrade.isPresent()) {
            grade = existingGrade.get();
            grade.setValue(request.getValue());
        } else {
            grade = new QuoteGrade();
            grade.setQuote(quote);
            grade.setReader(reader);
            grade.setValue(request.getValue());
        }

        QuoteGrade savedGrade = quoteGradeRepository.save(grade);

        updateQuoteRating(quote.getId());

        return savedGrade;
    }

    public void updateQuoteRating(Integer quoteId) {
        Double averageRating = quoteGradeRepository.findAverageGradeByQuoteId(quoteId);
        quoteRepository.findById(quoteId).ifPresent(quote -> {
            quote.setRating(averageRating);
            quoteRepository.save(quote);
        });
    }
}

