import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Acao {

    private String ticker;
    private List<Negociacao> negociacoes;
}
