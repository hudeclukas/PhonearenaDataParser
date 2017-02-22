package fiit.vi.parser;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lukáš on 15-Oct-16.
 */
public class SpecsWebPage {

    public class Price {

        public long price = 0;
        public String interval = "";
    }
    public static final String NETWORK = "NETWORK";

    public static final String LAUNCH = "LAUNCH";
    public static final String ANNOUNCED = "ANNOUNCED";
    public static final String RELEASED = "RELEASED";
    public static final String BODY = "BODY";
    public static final String DISPLAY = "DISPLAY";
    public static final String PLATFORM = "PLATFORM";
    public static final String MEMORY = "MEMORY";
    public static final String RAM = "RAM";
    public static final String CAMERA = "CAMERA";
    public static final String SOUND = "SOUND";
    public static final String COMMS = "COMMS";
    public static final String FEATURES = "FEATURES";
    public static final String BATTERY = "BATTERY";
    public static final String MISC = "MISC";
    public static final String PRICE = "PRICE";
    public static final String P_GROUP = "PRICE GROUP";
    private String fullSpecs;

    private String title = "";

    private String name = "";
    private String brand = "";
    private String network = "";
    private String announced = "";
    private String released = "";
    private String body = "";
    private String display = "";
    private List<KeyValue<String>> platform = new LinkedList<>();
    private List<KeyValue<String>> memory = new LinkedList<>();
    private String camera = "";
    private String sound = "";
    private String comms = "";
    private String features = "";
    private String battery = "";
    private String miscellaneous = "";
    private long price = 0;
    private String priceGroup = "";
    private List<String> comments = new LinkedList<>();

    @Override
    public String toString() {
        String output = network + "; " + announced + "; " + body + "; " + display + "; ";
        for (KeyValue<String> val :
                platform) {
            output += val;
        }
        for (KeyValue<String> val :
                memory) {
            output += val;
        }
        output += camera + "; " + sound + "; " + comms + "; " + features + "; " + battery + "; " + miscellaneous;

        return output;
    }

    public void setFullSpecs(String fullSpecs) {
        this.fullSpecs = fullSpecs;
    }

    public String getFullSpecs() {
        return fullSpecs;
    }

    public void addPlatformInfo(String type, String info) {
        if (platform == null) {
            platform = new LinkedList<>();
        }
        platform.add(new KeyValue<String>(type, info));
    }

    public void addMemoryInfo(String type, String info) {
        if (memory == null) {
            memory = new LinkedList<>();
        }
        memory.add(new KeyValue<String>(type, info));
    }

    public void addComment(String commentary) {
        if (comments == null) {
            comments = new LinkedList<>();
        }
        comments.add(commentary);
    }

    public JSONObject getPlatform() {
        JSONObject object = new JSONObject();
        for (KeyValue kv :
                platform) {
            object.put(kv.getKey().toLowerCase(), kv.getValue());
        }
        return object;
    }

    public JSONObject getMemory() {
        JSONObject object = new JSONObject();
        for (KeyValue kv :
                memory) {
            object.put(kv.getKey().toLowerCase(), kv.getValue());
        }
        return object;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getCamera() {
        return camera;
    }

    public String getComments() {
        String commentaries = "";
        for (String comment :
                comments) {
            commentaries += comment;
        }
        return commentaries;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getAnnounced() {
        return announced;
    }

    public String getReleased() {
        return released;
    }

    public void setLaunch(String launch) {
        List<String> launchDates = new LinkedList<>();
        DateParser.parseDate(launch, launchDates);
        if (launchDates.size() >= 1) {
            announced = launchDates.get(0);
            released = launchDates.get(0);
        }
        if (launchDates.size() == 2) {
            released = launchDates.get(1);
        }
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getComms() {
        return comms;
    }

    public void setComms(String comms) {
        this.comms = comms;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getMiscellaneous() {
        return miscellaneous;
    }

    public long getPrice() {
        return price;
    }

    public String getPriceGroup() {
        return priceGroup;
    }

    public void setMiscellaneous(String miscellaneous) {
        Price price = new Price();
        this.miscellaneous = PriceParser.parsePrice(miscellaneous, price);
        this.price = price.price;
        this.priceGroup = price.interval;
    }
}
