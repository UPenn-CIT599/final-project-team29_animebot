package MyAnimeList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class Review extends MyAnimeListObject {
    private static Map<String, String> REVIEW_PROPERTY_TYPES = new HashMap<>();
    static {
        REVIEW_PROPERTY_TYPES.put("content", "string");
        REVIEW_PROPERTY_TYPES.put("helpful_count", "integer");
        REVIEW_PROPERTY_TYPES.put("mal_id", "integer");
        REVIEW_PROPERTY_TYPES.put("reviewer", "json");
        REVIEW_PROPERTY_TYPES.put("url", "string");
    }
    
    private User reviewer;
    
    public Review(int id) {
        super(id, REVIEW_PROPERTY_TYPES);
    }
    
    public String getContent() {
        return getStringValue("content");
    }
    
    public int getHelpfulCount() {
        return getIntegerValue("helpful_count");
    }
    
    public User getReviewer() {
        if (reviewer == null) {
            setReviewer();
        }
        
        return reviewer;
    }
    
    public String getUrl() {
        return getStringValue("url"); 
    }
    
    private boolean setReviewer() {
        JSONObject jObj = getJsonValue("reviewer");
        try {
            User reviewer = MyAnimeList.getUser(jObj.getString("username"));
            this.reviewer = reviewer;
            return true;
        } catch (JSONException e) {
            return false;
        }
    }
}
