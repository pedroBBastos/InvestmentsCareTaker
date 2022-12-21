package negotiationsParser;

public class NegociacaoParser {
    public static Negociacao parseNegociacaoRow(String row) {
        String[] tokens = row.split(" ");
        return Negociacao.builder()
                .operacao(tokens[1].charAt(0))
                .tipoDeMercado(tokens[2])
                .ticker(tokens[3])
                .quantidade(Integer.parseInt(tokens[tokens.length-4]))
                .precoUnitario(Float.parseFloat(treatNumberString(tokens[tokens.length-3])))
                .valorTotal(Float.parseFloat(treatNumberString(tokens[tokens.length-2])))
                .build();
    }

    private static String treatNumberString(String number) {
        return number.replaceAll("\\.", "")
                .replaceAll(",", ".");
    }
}
