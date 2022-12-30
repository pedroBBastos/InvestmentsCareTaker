package dolarParser;

import extractParser.ExtractParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        InputStream inputStream = classLoader.getResourceAsStream("cotacoesDolarEmReais.json");
        String fileName = null;

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            //JSON parser object to parse read file
            JSONParser jsonParser = new JSONParser();

            try {
                //Read JSON file
                Object obj = jsonParser.parse(inputStreamReader);

                JSONArray dolarPriceList = (JSONArray) obj;
                List<CotacaoDolar> cotacoesDolar = new ArrayList<>();

                dolarPriceList.forEach(o -> {
                    JSONObject jsonObject = (JSONObject) o;
                    Float valor = Float.parseFloat((String) jsonObject.get("ask"));
                    Date data = new Date(Long.parseLong((String) jsonObject.get("timestamp"))*1000);
//                    java.sql.Date data = new java.sql.Date(Long.parseLong((String) jsonObject.get("timestamp"))*1000);
                    cotacoesDolar.add(CotacaoDolar.builder()
                            .valor(valor)
                            .data(data)
                            .build());
                });

                inputStreamReader.close();

                repository.saveAll(cotacoesDolar);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
