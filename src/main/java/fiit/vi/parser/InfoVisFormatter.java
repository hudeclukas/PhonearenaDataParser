package fiit.vi.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Lukáš on 20-Feb-17.
 */
public class InfoVisFormatter {

    enum GROUP_BY {
        BRAND, DISPLAY, PRICE;
    }
    private List<JSONObject> phones;

    public InfoVisFormatter(List<JSONObject> phones) {
        this.phones = phones;
    }

    public JSONObject groupBy(GROUP_BY group_key) {
        switch(group_key) {
            case BRAND :
                return groupByBrand();
            case DISPLAY:
                return groupByDisplay();
            case PRICE:
                return groupByPrice();
            default :
                return groupByBrand();
        }
    }

    private JSONObject groupByDisplay() {
        return null;
    }

    private JSONObject groupByPrice() {
        return null;
    }

    private JSONObject groupByBrand() {

        Node root = new Node("Phones", "root");

        for (JSONObject phone : phones) {
            String brand = phone.getString("brand");
            Double display = -1.0;
            Double camera = -1.0;
            Double ram = -1.0;
            Long price = -1L;
            try {
                display = phone.getDouble(SpecsWebPage.DISPLAY.toLowerCase());
            } catch (JSONException e) {
                System.out.println("display not found");
            }
            try {
                camera = phone.getDouble(SpecsWebPage.CAMERA.toLowerCase());
            } catch (JSONException e) {
                System.out.println("camera not found");
            }
            try {
                ram = phone.getDouble(SpecsWebPage.RAM.toLowerCase());
            } catch (JSONException e) {
                System.out.println("ram not found");
            }
//            String memory = phone.getString(SpecsWebPage.MEMORY.toLowerCase());
//            String weight = phone.getString(SpecsWebPage.BODY.toLowerCase());
            try {
                price = phone.getLong(SpecsWebPage.PRICE.toLowerCase());
            } catch (JSONException e) {
                System.out.println("price not found");
            }
//            String price_group = phone.getString(SpecsWebPage.P_GROUP.toLowerCase());
            String name = phone.getString("name");

            if (root.get(brand) == null) {
                root.put(brand, new Node(brand, brand));
            }
            if (root.get(brand).get(display.toString()) == null) {
                root.get(brand).put(display.toString(),new Node("display: " + display.toString() + "\"", display));
            }
            if (root.get(brand).get(display.toString()).get(camera.toString()) == null) {
                root.get(brand).get(display.toString()).put(camera.toString(), new Node("camera: " + camera.toString() + " MP", camera));
            }
            if (root.get(brand).get(display.toString()).get(camera.toString()).get(ram.toString()) == null) {
                root.get(brand).get(display.toString()).get(camera.toString()).put(ram.toString(), new Node("ram: " + ram.toString() + " GB", ram));
            }
            if (root.get(brand).get(display.toString()).get(camera.toString()).get(ram.toString()).get(price.toString()) == null) {
                root.get(brand).get(display.toString()).get(camera.toString()).get(ram.toString()).put(price.toString(), new Node("price: " + price.toString(), price));
            }
            if (root.get(brand).get(display.toString()).get(camera.toString()).get(ram.toString()).get(price.toString()).get(name) == null) {
                root.get(brand).get(display.toString()).get(camera.toString()).get(ram.toString()).get(price.toString()).put(name, new Node(name, name));
            }
        }
        return root.toJSON();
    }
}
