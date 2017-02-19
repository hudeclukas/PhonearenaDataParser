package fiit.vi.parser;

import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Lukáš on 15-Oct-16.
 */
public class Main {

    public static final String PATH_TO_HTMLS = "D:\\Datasety-VI-gsmarena\\gsmarena phones\\www.gsmarena.com\\";
    public static final String JSON_EXPORT = "C:\\Users\\Lukas\\WebstormProjects\\VD_phonevis\\data\\";
    public static final String PATH_TO_HTMLS2 = "D:\\Datasety-VI-gsmarena\\gsmarena phones\\test\\";

    public static void main(String[] args) throws IOException {

        List<File> htmlFiles = null;
        try {
            htmlFiles = Files.walk(Paths.get(PATH_TO_HTMLS)).filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        PhoneHtmlFileParser parser = new PhoneHtmlFileParser();

        int phones = 0;
        List<JSONObject> allPhones = new LinkedList<>();

        for (File file : htmlFiles) {
            JSONObject json = new JSONObject();
            boolean isPhone = parser.parseFileToJson(file, PATH_TO_HTMLS+"json\\", json);
            if (isPhone) {
                phones++;
                json.put("id",phones);
                allPhones.add(json);
            }
        }

        File jsonFile = new File(JSON_EXPORT + "phones.json");
        OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(jsonFile), StandardCharsets.UTF_8);
        for (JSONObject phone : allPhones) {
            fileWriter.write("{\"index\":{\"_id\":\"" + phone.get("id")+ "\"}}\n");
            fileWriter.write(phone.toString());
            fileWriter.write("\n");
        }
        fileWriter.flush();
        fileWriter.close();

        System.out.println("Parsed " + htmlFiles.size() + " files. Phone files: " + phones + ".");
    }
}
