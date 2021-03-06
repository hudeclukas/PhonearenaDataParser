package fiit.vi.parser;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Luk� on 15-Oct-16.
 */
public class PhoneHtmlFileParser {

    public static final String SPECS_LIST_ID = "specs-list";
    public static final String USER_COMMENTS_ID = "user-comments";
    public static final String USER_COMMENT_THREAD_ID = "user-thread";

    public boolean parseFileToJson(File file, String jsonPath, JSONObject json) {

        SpecsWebPage web = new SpecsWebPage();
        if (parseHtmlFile(file, web) == false) return false;
        jsonPath += file.getName() + ".json";

        return saveToJson(web, jsonPath, json);
    }

    public boolean parseHtmlFile(File file, SpecsWebPage web) {
        try {
            Document doc;

            doc = Jsoup.parse(file, "UTF-8");

            String title = doc.title();
            if (!isPhoneFile(title)) return false;


            Element body = doc.body();

            Element specs = body.getElementById(SPECS_LIST_ID);

            List<Element> specTables = specs.getElementsByTag("table");

            web.setFullSpecs(specs.text());

            for (Element specInfo :
                    specTables) {
                readElement(specInfo, web);
            }

            /* Parse user comments */
            Element comments = body.getElementById(USER_COMMENTS_ID);
            if (comments != null) {
                final Elements userComments = comments.getElementsByClass(USER_COMMENT_THREAD_ID);

                for (Element comment : userComments) {
                    String text = comment.text();
                    if (text.isEmpty()) continue;

                    web.addComment(text);
                }
            }
            web.setTitle(title);

            System.out.println(title + " --> parsed");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean saveToJson(SpecsWebPage web, String jsonPath, JSONObject phoneJson) {
        File jsonFile = new File(jsonPath);
        File jsonDir = jsonFile.getParentFile();

        if (!jsonDir.exists()) {
            if (jsonDir.mkdir() == false) return false;
        }

        phoneJson.put("title", web.getTitle());
        //phoneJson.put("specs", web);
        phoneJson.put(SpecsWebPage.BATTERY.toLowerCase(),web.getBattery());
        phoneJson.put(SpecsWebPage.BODY.toLowerCase(),web.getBody());
        phoneJson.put(SpecsWebPage.CAMERA.toLowerCase(),web.getCamera());
        phoneJson.put(SpecsWebPage.COMMONS.toLowerCase(),web.getComms());
        phoneJson.put(SpecsWebPage.DISPLAY.toLowerCase(),web.getDisplay());
        phoneJson.put(SpecsWebPage.FEATURES.toLowerCase(),web.getFeatures());
        phoneJson.put(SpecsWebPage.ANNOUNCED.toLowerCase(),web.getAnnounced());
        phoneJson.put(SpecsWebPage.RELEASED.toLowerCase(),web.getReleased());
        phoneJson.put(SpecsWebPage.MEMORY.toLowerCase(),web.getMemory());
        phoneJson.put(SpecsWebPage.MISC.toLowerCase(),web.getMiscellaneous());
        phoneJson.put(SpecsWebPage.PRICE.toLowerCase(),web.getPrice());
        phoneJson.put(SpecsWebPage.P_GROUP.toLowerCase(),web.getPriceGroup());
        phoneJson.put(SpecsWebPage.NETWORK.toLowerCase(),web.getNetwork());
        phoneJson.put(SpecsWebPage.PLATFORM.toLowerCase(),web.getPlatform());
        phoneJson.put(SpecsWebPage.SOUND.toLowerCase(),web.getSound());
        phoneJson.put("comments",web.getComments());

        try {
            jsonFile.createNewFile();
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(phoneJson.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void readElement(Element specInfo, SpecsWebPage web) {
        Element th = specInfo.getElementsByTag("th").first();
        String header = th.text();
        String text = "";
        text = getTableContents(specInfo);
        String elementInfo = header + ": " + text;
        switch (header.toUpperCase()) {

            case SpecsWebPage.NETWORK:
                web.setNetwork(elementInfo);
                break;

            case SpecsWebPage.LAUNCH:
                web.setLaunch(elementInfo);
                break;

            case SpecsWebPage.BODY:
                web.setBody(elementInfo);
                break;
            case SpecsWebPage.DISPLAY:
                web.setDisplay(elementInfo);
                break;
            case SpecsWebPage.PLATFORM:
                for (Element tr : specInfo.getElementsByTag("tr")) {
                    Elements td = tr.getElementsByTag("td");
                    web.addPlatformInfo(td.get(0).text(), td.get(1).text());
                }
                break;
            case SpecsWebPage.MEMORY:
                for (Element tr : specInfo.getElementsByTag("tr")) {
                    Elements td = tr.getElementsByTag("td");
                    web.addMemoryInfo(td.get(0).text(), td.get(1).text());
                }
                break;
            case SpecsWebPage.CAMERA:
                web.setCamera(elementInfo);
                break;
            case SpecsWebPage.SOUND:
                web.setSound(elementInfo);
                break;
            case SpecsWebPage.COMMONS:
                web.setComms(elementInfo);
                break;
            case SpecsWebPage.FEATURES:
                web.setFeatures(elementInfo);
                break;
            case SpecsWebPage.BATTERY:
                web.setBattery(elementInfo);
                break;
            case SpecsWebPage.MISC:
                web.setMiscellaneous(elementInfo);
                break;
        }

    }

    private String getTableContents(Element specInfo) {
        String text = "";
        for (Element tr : specInfo.getElementsByTag("tr")) {
            Elements td = tr.getElementsByTag("td");
            if (td.isEmpty()) break;
            if (td.get(1).text().equalsIgnoreCase("no")) {
                continue;
            }
            if (!td.get(0).text().isEmpty()) {
                text += td.get(0).text() + ": ";
            }
            if (td.get(1).text().equalsIgnoreCase("yes")) {
                continue;
            }
            text += td.get(1).text() + "; ";
        }
        return text;
    }


    public boolean isPhoneFile(String title) {
        if (title.contains("Full phone specifications") || title.contains("Full tablet specifications"))
            return true;
        else return false;
    }
}
