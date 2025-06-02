package ttv.poltoraha.pivka.dao.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteGradeRequestDto {
    private Integer quoteId;
    private String readerUsername;
    private Integer value;
}
