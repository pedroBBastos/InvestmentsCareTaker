package dolarFillMissingDays;

import dolarParser.CotacaoDolar;
import dolarParser.CotacaoDolarRepository;
import org.javatuples.Pair;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.*;

@SpringBootApplication
@ComponentScan(basePackages = {"dolarFillMissingDays", "dolarParser"})
public class FillMissingDolarPrices implements CommandLineRunner {

    // BIG TODO: Jogar os repositories para outro diretório para não acabar tbem
    // importando os outros SpringBootApplication (dolarParser) pelo ComponentScan...
    // pq como está acaba executando o outro tbem...

    @Autowired
    private CotacaoDolarRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(FillMissingDolarPrices.class, args);
    }

    @Override
    public void run(String... args) {
        Map<Pair<Integer, Integer>, Float> map = missingDolarPrices();
        List<CotacaoDolar> cotacoesDolar = new ArrayList<>();

        DateTime start = DateTime.parse("2020-1-1");
        System.out.println("Start: " + start);

        DateTime end = DateTime.parse("2021-10-05");
        System.out.println("End: " + end);

        List<DateTime> between = getDateRange(start, end);
        for (DateTime d : between) {
            Float valor = map.get(new Pair<>(d.getMonthOfYear(), d.getYear()));
            Date data = d.toDate();

            CotacaoDolar cotacaoDolar = CotacaoDolar.builder()
                    .valor(valor)
                    .data(data)
                    .build();

            cotacoesDolar.add(cotacaoDolar);
            System.out.println(cotacaoDolar);
        }

        repository.saveAll(cotacoesDolar);
    }

    public static Map<Pair<Integer, Integer>, Float> missingDolarPrices() {
        Map<Pair<Integer, Integer>, Float> map = new HashMap<>();

        // Considerando meses de 01/01/2020 a 05/10/2021

        map.put(new Pair<>(1, 2020), 4.09f);
        map.put(new Pair<>(2, 2020), 4.16f);
        map.put(new Pair<>(3, 2020), 4.31f);
        map.put(new Pair<>(4, 2020), 4.73f);
        map.put(new Pair<>(5, 2020), 5.25f);
        map.put(new Pair<>(6, 2020), 5.82f);
        map.put(new Pair<>(7, 2020), 5.18f);
        map.put(new Pair<>(8, 2020), 5.34f);
        map.put(new Pair<>(9, 2020), 5.38f);
        map.put(new Pair<>(10, 2020), 5.27f);
        map.put(new Pair<>(11, 2020), 5.61f);
        map.put(new Pair<>(12, 2020), 5.48f);

        map.put(new Pair<>(1, 2021), 5.09f);
        map.put(new Pair<>(2, 2021), 5.27f);
        map.put(new Pair<>(3, 2021), 5.38f);
        map.put(new Pair<>(4, 2021), 5.62f);
        map.put(new Pair<>(5, 2021), 5.62f);
        map.put(new Pair<>(6, 2021), 5.27f);
        map.put(new Pair<>(7, 2021), 5.08f);
        map.put(new Pair<>(8, 2021), 5.10f);
        map.put(new Pair<>(9, 2021), 5.24f);
        map.put(new Pair<>(10, 2021), 5.25f);

        return map;
    }

    public static List<DateTime> getDateRange(DateTime start, DateTime end) {
        List<DateTime> ret = new ArrayList<>();
        DateTime tmp = start;
        while(tmp.isBefore(end) || tmp.equals(end)) {
            ret.add(tmp);
            tmp = tmp.plusDays(1);
        }
        return ret;
    }
}
