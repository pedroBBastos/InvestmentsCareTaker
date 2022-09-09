import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Builder
@Data
public class Pregao implements Comparable<Pregao> {

    private Date dataPregao;
    private List<Negociacao> negociacoes;

    @Override
    public int compareTo(Pregao o) {
        return this.getDataPregao().compareTo(o.getDataPregao());
    }
}
