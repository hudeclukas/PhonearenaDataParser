package fiit.vi.parser;

import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
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
        List<JSONObject> allPhones = new ArrayList<>();

        for (File file : htmlFiles) {
            JSONObject json = new JSONObject();
            boolean isPhone = parser.parseFileToJson(file, PATH_TO_HTMLS + "json\\", json, false);
            if (isPhone) {
                phones++;
                json.put("id", phones);
                allPhones.add(json);
            }
        }

        /* Select random only K phones */
        HashSet<Integer> idx = new HashSet<>();
        List<JSONObject> kPhones = new ArrayList<>();
        while (idx.size() < 100) {
            idx.add(ThreadLocalRandom.current().nextInt(0,allPhones.size()));
        }
        Iterator<Integer> idk = idx.iterator();
        while (idk.hasNext()) {
            kPhones.add(allPhones.get(idk.next()));
        }

        /* Parse phones into InfoVis readable format */
        InfoVisFormatter ivf = new InfoVisFormatter(kPhones);
        JSONObject phoneBrand = ivf.groupBy(InfoVisFormatter.GROUP_BY.BRAND);
//        JSONObject phoneDisplay = ivf.groupBy(InfoVisFormatter.GROUP_BY.DISPLAY);
//        JSONObject phonePrice = ivf.groupBy(InfoVisFormatter.GROUP_BY.PRICE);

        File brandFile = new File(JSON_EXPORT + "brandTree.json");
        OutputStreamWriter fw1 = new OutputStreamWriter(new FileOutputStream(brandFile), StandardCharsets.UTF_8);
        fw1.write(phoneBrand.toString());
        fw1.flush();
        fw1.close();

//        File jsonFile = new File(JSON_EXPORT + "phonesIndividual.json");
//        OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(jsonFile), StandardCharsets.UTF_8);
//        for (JSONObject phone : allPhones) {
//            fileWriter.write("{\"index\":{\"_id\":\"" + phone.get("id")+ "\"}}\n");
//            fileWriter.write(phone.toString());
//            fileWriter.write("\n");
//        }
//        fileWriter.write(allPhones.toString());
//        fileWriter.flush();
//        fileWriter.close();

        System.out.println("Parsed " + htmlFiles.size() + " files. Phone files: " + phones + ".");
    }
}
