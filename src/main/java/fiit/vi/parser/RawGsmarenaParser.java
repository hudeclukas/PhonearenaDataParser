package fiit.vi.parser;

import org.json.JSONObject;

import java.util.StringTokenizer;

/**
 * Created by Lukáš on 20-Feb-17.
 */
public class RawGSMarenaParser {

    int parseBattery(String text) {
        return parseIntBefore(text, "mah", " ");
    }

    double parseWeight(String text) {
        return parseDoubleAfter(text, "weight:", " ");
    }

    public double parseCameraMP(String text) {
        return parseDoubleAfter(text, "primary:", " ");
    }

    public double parseDisplay(String text) {
        return parseDoubleAfter(text, "size:", " ");
    }

    public int parseMemory(String text) {
        return parseIntBefore(text, "gb", " ");
    }

    public int parseRam(String text) {
        StringTokenizer st = new StringTokenizer(text, " ", false);

        String tokenI = st.nextToken();
        String tokenII = st.nextToken();
        while (st.hasMoreTokens()) {
            String tokenIII = st.nextToken();
            if (tokenIII.equalsIgnoreCase("ram")) {
                return Integer.parseInt(tokenI);
            }
            tokenI = tokenII;
            tokenII = tokenIII;
        }
        return 0;
    }

    private double parseDoubleAfter(String text, String phrase, String delimiter) {
        StringTokenizer st = new StringTokenizer(text, delimiter, false);

        while (st.hasMoreTokens()) {
            if (st.nextToken().equalsIgnoreCase(phrase)) {
                return Double.parseDouble(st.nextToken());
            }
        }
        return 0;
    }

    private int parseIntBefore(String text, String phrase, String delimiter) {
        StringTokenizer st = new StringTokenizer(text, delimiter, false);

        String tokenI = st.nextToken();
        while (st.hasMoreTokens()) {
            String tokenII = st.nextToken();
            if (tokenII.equalsIgnoreCase(phrase)) {
                return Integer.parseInt(tokenI);
            }
            tokenI = tokenII;
        }
        return 0;
    }

    private int parseIntAfter(String text, String phrase, String delimiter) {
        StringTokenizer st = new StringTokenizer(text, delimiter, false);

        while (st.hasMoreTokens()) {
            if (st.nextToken().equalsIgnoreCase(phrase)) {
                return Integer.parseInt(st.nextToken());
            }
        }
        return 0;
    }
}
