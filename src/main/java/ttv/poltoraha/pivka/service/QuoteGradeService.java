package ttv.poltoraha.pivka.service;

import ttv.poltoraha.pivka.dao.request.QuoteGradeRequestDto;
import ttv.poltoraha.pivka.entity.QuoteGrade;

public interface QuoteGradeService {
    public QuoteGrade addGradeToQuote(QuoteGradeRequestDto request);
    public void updateQuoteRating(Integer quoteId);
}
