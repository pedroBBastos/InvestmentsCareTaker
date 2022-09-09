import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PDFBoxTest {
    public static void main(String[] args) throws IOException {

        List<Pregao> pregoes = new ArrayList<>();
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
                        negociacoes.addAll(negociacaoList);

                        String dataPregao = Arrays.stream(text.split("\n"))
                                .filter(s -> s.matches("[0-9]{2}\\/[0-9]{2}\\/[0-9]{4}\r"))
                                .findFirst().orElse(null);

                        try {
                            pregoes.add(Pregao.builder()
                                    .negociacoes(negociacaoList)
                                    .dataPregao(new SimpleDateFormat("dd/MM/yyyy").parse(dataPregao))
                                    .build());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });

        // quero obter quais açoes que hoje estão na minha carteira e suas respectivas listas de negociacoes
        List<Acao> acoesEmCarteira = negociacoes.stream()
                .collect(Collectors.groupingBy(Negociacao::getTicker))
                .entrySet().stream()
                .filter(tickerNegociacoesEntry -> {
                    AtomicInteger qtdeFinal = new AtomicInteger();
                    tickerNegociacoesEntry.getValue().forEach(negociacao -> {
                        if (negociacao.getOperacao() == 'C') {
                            qtdeFinal.addAndGet(negociacao.getQuantidade());
                        } else {
                            qtdeFinal.addAndGet(-1 * negociacao.getQuantidade());
                        }
                    });
                    return qtdeFinal.get() != 0;
                })
                .map(stringListEntry -> Acao.builder()
                        .ticker(stringListEntry.getKey())
                        .negociacoes(stringListEntry.getValue()).build())
                .collect(Collectors.toList());
    }
}
