package fiit.vi.parser;

import org.json.JSONArray;
import org.json.JSONObject;

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
        JSONObject phones = new JSONObject();
        phones.put("name", "Phones");
        switch(group_key) {
            case BRAND :
                phones.put("children",groupByBrand());
                return phones;
            case DISPLAY:
                phones.put("children",groupByDisplay());
            case PRICE:
                return phones.put("children",groupByPrice());
            default :
                return phones.put("children", groupByBrand());
        }
    }

    private JSONArray groupByDisplay() {
        return null;
    }

    private JSONArray groupByPrice() {
        return null;
    }

    private JSONArray groupByBrand() {
        HashSet<String> brands;
        for (JSONObject phone : phones) {
            String brand = phone.getString("brand");
        }
        return null;
    }
}
