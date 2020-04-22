package myanimelist;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Base class for all MyAnimeList classes 
 */
public abstract class MyAnimeListObject {
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

    /**
     * Gets the id of the object
     * @return Id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the Boolean key's value of the object
     * @param Boolean key
     * @return Boolean value
     */
    protected boolean getBooleanValue(String key) {
        return booleanProperties.get(key);
    }

    /**
     * Gets the Double key's value of the object
     * @param Double key
     * @return Double value
     */
    protected double getDoubleValue(String key) {
        return doubleProperties.get(key);
    }

    /**
     * Gets the JSONObject key's value of the object
     * @param JSONObject key
     * @return JSONObject value
     */
    protected JSONObject getJsonValue(String key) {
        return jsonProperties.get(key);
    }

    /**
     * Gets the Integer key's value of the object
     * @param Integer key
     * @return Integer value
     */
    protected int getIntegerValue(String key) {
        return integerProperties.get(key);
    }

    /**
     * Gets the String key's value of the object
     * @param String key
     * @return String value
     */
    protected String getStringValue(String key) {
        return stringProperties.get(key);
    }

    /**
     * Updates the Boolean key's value
     * @param Boolean key
     * @param Boolean value
     */
    protected void setBooleanValue(String key, boolean value) {
        booleanProperties.put(key, value);
    }

    /**
     * Updates the Double key's value
     * @param Double key
     * @param Double value
     */
    protected void setDoubleValue(String key, double value) {
        doubleProperties.put(key, value);
    }

    /**
     * Updates the Integer key's value
     * @param Integer key
     * @param Integer value
     */
    protected void setIntegerValue(String key, int value) {
        integerProperties.put(key, value);
    }

    /**
     * Updates the JSONObject key's value
     * @param JSONObject key
     * @param JSONObject value
     */
    protected void setJsonValue(String key, JSONObject value) {
        jsonProperties.put(key, value);
    }

    /**
     * Updates the String key's value
     * @param String key
     * @param String value
     */
    protected void setStringValue(String key, String value) {
        stringProperties.put(key, value);
    }

    /**
     * Processes the JSON response and updates the object's values
     * @param response Stringified JSON response returned from MyAnimeList.makeAPICall
     * @throws JSONException
     */
    public void setValues(String response) {
        try {
            setValues(new JSONObject(response));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Processes the JSON response and updates the object's values
     * @param jsonResponse JSON response returned from MyAnimeList.makeAPICall
     * @throws JSONException
     */
    public void setValues(JSONObject jObj) {
        Iterator<?> keys = jObj.keys();
        String key, type, valueStr;
        
        while (keys.hasNext()) {
            key = (String) keys.next();

            if (!propertyTypes.containsKey(key)) {
                continue;
            }
            try {
                type = propertyTypes.get(key);
                valueStr = jObj.get(key).toString();

                switch(type) {
                    case "boolean":
                        if (valueStr == "null") {
                            valueStr = "false";
                        }
                        setBooleanValue(key, Boolean.parseBoolean(valueStr));
                        break;
                    case "double":
                        if (valueStr == "null") {
                            valueStr = "-1.0";
                        }
                        setDoubleValue(key, Double.parseDouble(valueStr));
                        break;
                    case "integer":
                        if (valueStr == "null") {
                            valueStr = "-1";
                        }
                        setIntegerValue(key, Integer.parseInt(valueStr));
                        break;
                    case "string":
                        if (valueStr == "null") {
                            valueStr = "";
                        }
                        setStringValue(key, valueStr);
                        break;
                    default:
                        setJsonValue(key, new JSONObject(valueStr));
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
