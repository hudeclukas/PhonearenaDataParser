package fiit.vi.parser;

/**
 * Created by Lukáš on 20-Oct-16.
 */
public class PriceParser {

    public static String parsePrice(String text, SpecsWebPage.Price price) {
        String tokens[] = text.split("\\s+");
        text = "";
        int i = 0;
        for (i = tokens.length - 1; i >= 0; i--) {
            if (tokens[i].replaceAll("[,.();:]","").equalsIgnoreCase("eur")) {
                price.price = Integer.valueOf(tokens[i-1]);
                price.interval = tokens[i-3];
                i = i-5;
                break;
            }
        }
        if (i <= 0) {
            i = tokens.length;
        }
        for (int j = 0; j < i; j++) {
            text += tokens[j] + " ";
        }
        return text;
    }
}
