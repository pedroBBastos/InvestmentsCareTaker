package extractParser;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class ExtractParser {

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "C:\\Users\\Pedro Bastos\\Downloads\\Extrato_2022-09-09.csv";

        List<Lancamento> beans = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Lancamento.class)
                .withSeparator(';')
                .withSkipLines(2)
                .build()
                .parse();

        beans.forEach(System.out::println);
    }
}
