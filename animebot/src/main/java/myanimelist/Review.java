package myanimelist;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User reviews on MyAnimeList
 */
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
    
    /**
     * Gets the content of the review
     * @return Review content
     */
    public String getContent() {
        return getStringValue("content");
    }
    
    /**
     * Gets the number of users who found this review helpful
     * @return Helpful count
     */
    public int getHelpfulCount() {
        return getIntegerValue("helpful_count");
    }
    
    /**
     * Gets the user profile of the reviewer
     * @return User profile
     */
    public User getReviewer() {
        if (reviewer == null) {
            setReviewer();
        }
        
        return reviewer;
    }
    
    /**
     * Gets the url to this review
     * @return Review url
     */
    public String getUrl() {
        return getStringValue("url"); 
    }
    
    /**
     * Updates the reviewer of this review
     * @return True if reviewer is successfully updated, False if not
     */
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
