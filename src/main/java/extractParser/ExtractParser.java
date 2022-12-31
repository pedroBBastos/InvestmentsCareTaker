package extractParser;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class ExtractParser implements CommandLineRunner {

    @Autowired
    private DividendoRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(ExtractParser.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String fileName = "C:\\Users\\Pedro Bastos\\Downloads\\Extrato_2022-12-31.csv";

        List<Lancamento> beans = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Lancamento.class)
                .withSeparator(';')
                .withSkipLines(2)
                .build()
                .parse();

        List<Dividendo> dividendos = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Pattern pattern = Pattern.compile("([A-Z]{4}[0-9]{1,2})");

        for(Lancamento lancamento : beans) {
            Matcher matcher = pattern.matcher(lancamento.getHistorico());
            MatchResult result = matcher.results().findFirst().orElse(null);
            if (result == null) {
                continue;
            }

            System.out.println(lancamento);

            String ticker = result.group(1);

            Date dataLiquidacao = null, dataMovimentacao = null;
            try {
                dataLiquidacao = dateFormat.parse(lancamento.getLiquidacao());
                dataMovimentacao = dateFormat.parse(lancamento.getMovimentacao());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Float valorLancamento = Float.parseFloat(lancamento
                    .getLancamento().replace(',', '.'));

            dividendos.add(Dividendo.builder()
                    .dataLiquidacao(dataLiquidacao)
                    .dataMovimentacao(dataMovimentacao)
                    .ticker(ticker)
                    .lancamento(valorLancamento)
                    .build());
        }

        repository.saveAll(dividendos);
    }
}
