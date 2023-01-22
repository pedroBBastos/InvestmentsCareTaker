package negotiationsParser;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@Entity
@Table(name = "negociacao")
public class Negociacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operacao")
    private char operacao;

    @Column(name = "tipoDeMercado")
    private String tipoDeMercado;

    @Column(name = "ticker")
    private String ticker;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "precoUnitario")
    private Float precoUnitario;

    @Column(name = "valorTotal")
    private Float valorTotal;

    @Column(name = "date", columnDefinition = "DATE")
    private Date date; // para poder depois buscar o preço do dolar nesta data
                       // e saber o valor real da ação...
}
