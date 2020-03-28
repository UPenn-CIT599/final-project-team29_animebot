package MyAnimeList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class MyAnimeListObject {
    protected int id;
    protected Map<String, String> propertyTypes;
    
    protected Map<String, Boolean> booleanProperties = new HashMap<>();
    protected Map<String, Double> doubleProperties = new HashMap<>();
    protected Map<String, Integer> integerProperties = new HashMap<>();
    protected Map<String, JSONObject> jsonProperties = new HashMap<>();
    protected Map<String, String> stringProperties = new HashMap<>();
    
    public MyAnimeListObject(int id, Map<String, String> propertyTypes) {
        this.id = id;
        this.propertyTypes = propertyTypes;
    }
    
    public int getId() {
        return id;
    }
    
    protected boolean getBooleanValue(String key) {
        return booleanProperties.get(key);
    }
    
    protected double getDoubleValue(String key) {
        return doubleProperties.get(key);
    }
    
    protected JSONObject getJsonValue(String key) {
        return jsonProperties.get(key);
    }
    
    protected int getIntegerValue(String key) {
        return integerProperties.get(key);
    }
    
    protected String getStringValue(String key) {
        return stringProperties.get(key);
    }
    
    protected void setBooleanValue(String key, boolean value) {
        booleanProperties.put(key, value);
    }
    
    protected void setDoubleValue(String key, double value) {
        doubleProperties.put(key, value);
    }
    
    protected void setIntegerValue(String key, int value) {
        integerProperties.put(key, value);
    }
    
    protected void setJsonValue(String key, JSONObject value) {
        jsonProperties.put(key, value);
    }
    
    protected void setStringValue(String key, String value) {
        stringProperties.put(key, value);
    }
    
    /**
     * Processes the JSON response and updates the object's values
     * @param response Stringified JSON response returned from MyAnimeList.makeAPICall
     * @throws JSONException
     */
    public void setValues(String response) throws JSONException {
        setValues(new JSONObject(response));
    }
    
    /**
     * Processes the JSON response and updates the object's values
     * @param jsonResponse JSON response returned from MyAnimeList.makeAPICall
     * @throws JSONException
     */
    public void setValues(JSONObject jObj) throws JSONException {
        Iterator<?> keys = jObj.keys();
        String key, type;
        String value;
        while (keys.hasNext()) {
            key = (String) keys.next();
            
            if (!propertyTypes.containsKey(key)) {
                continue;
            }
            type = propertyTypes.get(key);
            value = jObj.get(key).toString();
            
            switch(type) {
                case "boolean":
                    if (value == "null") {
                        value = "false";
                    }
                    setBooleanValue(key, Boolean.parseBoolean(value));
                    break;
                case "double":
                    if (value == "null") {
                        value = "-1.0";
                    }
                    setDoubleValue(key, Double.parseDouble(value));
                    break;
                case "integer":
                    if (value == "null") {
                        value = "-1";
                    }
                    setIntegerValue(key, Integer.parseInt(value));
                    break;
                case "string":
                    if (value == "null") {
                        value = "";
                    }
                    setStringValue(key, value);
                    break;
                default:
                    setJsonValue(key, new JSONObject(value));
                    break;
            }
        }
    }
}
