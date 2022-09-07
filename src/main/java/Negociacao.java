import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Negociacao {

    private char operacao;
    private String tipoDeMercado;
    private String ticker;
    private Integer quantidade;
    private Float precoUnitario;
    private Float valorTotal;
}
