package ttv.poltoraha.pivka.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name="review_grade")
@Data
public class ReviewGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String readerUsername;
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
    private Integer grade;
}
