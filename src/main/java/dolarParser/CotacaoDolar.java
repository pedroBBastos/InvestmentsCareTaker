package dolarParser;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "cotacao_dolar")
public class CotacaoDolar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor")
    private Float valor;

    @Column(name = "data", columnDefinition = "DATE")
    private Date data;
}
