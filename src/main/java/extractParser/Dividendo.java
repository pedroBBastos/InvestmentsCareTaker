package extractParser;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@Entity
@Table(name = "dividendo")
public class Dividendo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_liquidacao", columnDefinition = "DATE")
    private Date dataLiquidacao;

    @Column(name = "data_movimentacao", columnDefinition = "DATE")
    private Date dataMovimentacao;

    @Column(name = "ticker")
    private String ticker;

    @Column(name = "lancamento")
    private Float lancamento;
}
