package fiit.vi.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Lukáš on 22-Feb-17.
 */
public class Node {

    private static Random rnd = new Random();

    public Node(String name, Object value) {
        this.name = name;
        this.id = name.toLowerCase().replace(" ", "_") + rnd.nextInt();
        this.value = value;
    }

    public String name = "";
    public String id = "";
    public Object value = null;
    public JSONObject data = new JSONObject();
    public HashMap<String, Node> children = new HashMap<>(50);

    public Node get(String key) {
        try {
            return children.get(key);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Node put(String key, Node value) {
        return children.put(key, value);
    }

    public JSONObject toJSON() {
        JSONObject me = new JSONObject();
        me.put("name", name);
        me.put("id", id);
//        me.put("data", data);
        if (!children.isEmpty()) {
            JSONArray chld = new JSONArray();
            for (String key : children.keySet()) {
                chld.put(children.get(key).toJSON());
            }
            me.put("children", chld);
        }
        return me;
    }
}
