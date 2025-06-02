package ttv.poltoraha.pivka.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name="reviewGrade")
@Data
public class ReviewGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "reader_username")
    private Reader reader;
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
    private Integer value;
}
