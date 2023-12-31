package dolarParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.*;

@SpringBootApplication
public class DolarParser implements CommandLineRunner {

    @Autowired
    private CotacaoDolarRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(DolarParser.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ClassLoader classLoader = DolarParser.class.getClassLoader();
//        InputStream inputStream = classLoader.getResourceAsStream("cotacoesDolarEmReais.json");
        InputStream inputStream = classLoader.getResourceAsStream("cotacaoDolar2023.json");

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            //JSON parser object to parse read file
            JSONParser jsonParser = new JSONParser();

            try {
                //Read JSON file
                Object obj = jsonParser.parse(inputStreamReader);

                JSONArray dolarPriceList = (JSONArray) obj;
                Set<CotacaoDolar> cotacoesDolar = new HashSet<>();

                dolarPriceList.forEach(o -> {
                    JSONObject jsonObject = (JSONObject) o;
                    Float valor = Float.parseFloat((String) jsonObject.get("ask"));
                    Date data = new Date(Long.parseLong((String) jsonObject.get("timestamp"))*1000);
//                    java.sql.Date data = new java.sql.Date(Long.parseLong((String) jsonObject.get("timestamp"))*1000);
                    CotacaoDolar cotacaoDolar = CotacaoDolar.builder()
                            .valor(valor)
                            .data(data)
                            .build();
                    cotacoesDolar.add(cotacaoDolar);
                });

                inputStreamReader.close();

                List<CotacaoDolar> cotacaoDolarList = new ArrayList<>(cotacoesDolar);
                cotacaoDolarList.sort(CotacaoDolar::compareTo);
                cotacaoDolarList.forEach(System.out::println);

                repository.saveAll(cotacaoDolarList);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
