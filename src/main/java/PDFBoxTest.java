import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PDFBoxTest {
    public static void main(String[] args) throws IOException {

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
                    }
                });
    }
}
