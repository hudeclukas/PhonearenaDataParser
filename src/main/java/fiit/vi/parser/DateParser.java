package fiit.vi.parser;

import java.util.List;

/**
 * Created by Lukáš on 19-Oct-16.
 */
public class DateParser {

    public static void parseDate(String text, List<String> date) {
        String tokens[] = text.split("\\s+");
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].contains("Announced")) {
                findDate(date, tokens, i);

//                    if (tokens.length > 8) {
//                        int month = 0;
//                        try {
//                            month = getMonth(tokens[i + 2].replaceAll("[,.:-;]", ""));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            month = 1;
//                        }
//                        announced += month < 10 ? "0" + month : month;
//                        date.add(announced);
//                        i += 3;
//                    } else {
//                        announced += "01";
//                        date.add(announced);
//                    }
            }
            if (tokens[i].contains("Released")) {
                findDate(date, tokens, i);

//                    String released = tokens[i + 1].replaceAll("[,.:-;]", "") + "-";
//                    if (tokens.length > 8) {
//                        int month = 0;
//                        try {
//                            month = getMonth(tokens[i + 2].replaceAll("[,.:-;]", ""));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            month = 12;
//                        }
//                        released += month < 10 ? "0" + month : month;
//                        date.add(released);
//                        i += 2;
//                    } else {
//                        released += "01";
//                        date.add(released);
//                    }
            }
        }
    }

    private static void findDate(List<String> date, String[] tokens, int i) {
        int year = 1999, month = 1;
        for (int j = i; j < tokens.length; j++) {
            try {
                year = Integer.valueOf(tokens[j].replaceAll("[,.:-;]", ""));
                month = getMonth(tokens[j + 1].replaceAll("[,.:-;]", ""));
                break;
            } catch (NumberFormatException e) {
                System.out.println(tokens[j]);
            } catch (Exception e) {
                e.printStackTrace();
                month = 1;
                System.out.println(tokens[j]);
            }
        }
        String datum = year + "-";
        datum += month < 10 ? "0" + month : month;
        date.add(datum);
    }

    private static int getMonth(String month) throws Exception {
        switch (month.toLowerCase()) {
            case "january":
                return 1;
            case "february":
                return 2;
            case "march":
                return 3;
            case "april":
                return 4;
            case "may":
                return 5;
            case "june":
                return 6;
            case "july":
                return 7;
            case "august":
                return 8;
            case "september":
                return 9;
            case "october":
                return 10;
            case "november":
                return 11;
            case "december":
                return 12;
            case "q1":
                return 2;
            case "q2":
                return 5;
            case "q3":
                return 8;
            case "q4":
                return 11;
            default:
                throw new Exception("parse exception\nMonth parameter is:" + month);
        }
    }
}