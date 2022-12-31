package negotiationsParser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private NegociacaoRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        List<Negociacao> negociacoes = new ArrayList<>();

        Files.walk(Paths.get("C:\\Users\\Pedro Bastos\\Desktop\\notasNegociacaoB3"))
                .filter(Files::isRegularFile)
                .forEach(pdfFile -> {

                    PDDocument document = null;
                    try {
                        System.out.println(pdfFile.toString());
                        document = PDDocument.load(new File(pdfFile.toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    PDFTextStripper pdfStripper = null;
                    String text = null;
                    try {
                        pdfStripper = new PDFTextStripper();
                        text = pdfStripper.getText(document);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        document.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (text != null) {
                        List<Negociacao> negociacaoList = Arrays.stream(text.split("\n"))
                                .filter(s -> s.contains("BOVESPA"))
                                .map(NegociacaoParser::parseNegociacaoRow)
                                .collect(Collectors.toList());
                        negociacaoList.forEach(System.out::println);

                        String dataPregao = Arrays.stream(text.split("\n"))
                                .filter(s -> s.matches("[0-9]{2}\\/[0-9]{2}\\/[0-9]{4}\r"))
                                .findFirst().orElse(null);

                        negociacaoList.forEach(negociacao -> {
                            try {
                                negociacao.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(dataPregao));
                                if (negociacao.getTipoDeMercado().equals("FRACIONARIO")) {
                                    String ticker = negociacao.getTicker();
                                    negociacao.setTicker(ticker.substring(0, ticker.length()-1));
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        });
                        negociacoes.addAll(negociacaoList);
                    }
                });

        repository.saveAll(negociacoes);
    }
}
