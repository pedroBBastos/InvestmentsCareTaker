import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class Negociacao {

    private char operacao;
    private String tipoDeMercado;
    private String ticker;
    private Integer quantidade;
    private Float precoUnitario;
    private Float valorTotal;

    private Date date; // para poder depois buscar o preço do dolar nesta data
                       // e saber o valor real da ação...
}
