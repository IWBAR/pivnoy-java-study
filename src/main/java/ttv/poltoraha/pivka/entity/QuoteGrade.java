package ttv.poltoraha.pivka.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity(name="quoteGrade")
@Data
public class QuoteGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "reader_username")
    private Reader reader;
    @ManyToOne
    @JoinColumn(name = "quote_id")
    private Quote quote;
    private Integer value;
}
