package ttv.poltoraha.pivka.dao.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewGradeRequestDto {
    private Integer reviewId;
    private String readerUsername;
    private Integer grade;
}