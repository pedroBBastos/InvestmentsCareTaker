package extractParser;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class Lancamento {

    @CsvBindByPosition(position = 0)
    private String liquidacao;

    @CsvBindByPosition(position = 1)
    private String movimentacao;

    @CsvBindByPosition(position = 2)
    private String historico;

    @CsvBindByPosition(position = 3)
    private String lancamento;

    @CsvBindByPosition(position = 4)
    private String saldo;

    @CsvBindByPosition(position = 5)
    private Integer codigo;

    @CsvBindByPosition(position = 6)
    private Integer statementNumber;

    @Override
    public String toString() {
        return "Data Liquidacao: " + liquidacao +
                ", Data Movimentacao: " + movimentacao +
                ", Historico: " + historico +
                ", Lançamento: " + lancamento +
                ", Saldo: " + saldo +
                ", Código: " + codigo;
    }
}
