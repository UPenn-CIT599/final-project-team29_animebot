package MyAnimeList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyAnimeListMedia extends MyAnimeListObject {
    protected static Map<String, String> MEDIA_PROPERTY_TYPES = new HashMap<>();
    static {
        MEDIA_PROPERTY_TYPES.put("background", "string");
        MEDIA_PROPERTY_TYPES.put("favorites", "integer");
        MEDIA_PROPERTY_TYPES.put("image_url", "string");
        MEDIA_PROPERTY_TYPES.put("mal_id", "integer");
        MEDIA_PROPERTY_TYPES.put("members", "integer");
        MEDIA_PROPERTY_TYPES.put("popularity", "integer");
        MEDIA_PROPERTY_TYPES.put("rank", "integer");
        MEDIA_PROPERTY_TYPES.put("related", "json");
        MEDIA_PROPERTY_TYPES.put("score", "double");
        MEDIA_PROPERTY_TYPES.put("status", "string");
        MEDIA_PROPERTY_TYPES.put("synopsis", "string");
        MEDIA_PROPERTY_TYPES.put("title", "string");
        MEDIA_PROPERTY_TYPES.put("title_english", "string");
        MEDIA_PROPERTY_TYPES.put("type", "string");
        MEDIA_PROPERTY_TYPES.put("url", "string");
    }
    protected ArrayList<News> articles;
    protected String media;
    protected ArrayList<String> pictures;
    protected ArrayList<Review> reviews;
    
    public MyAnimeListMedia (int id, String mediaType, Map<String, String> additionalPropertyTypes) {
        super(id, MEDIA_PROPERTY_TYPES);
        propertyTypes.putAll(additionalPropertyTypes);
        media = mediaType;
    }
    
    public String getBackground() {
        return getStringValue("background").toString();
    }
    
    public String getEnglishTitle() {
        return getStringValue("title_english").toString();
    }
    
    public int getFavorites() {
        return getIntegerValue("favorites");
    }
    
    public String getImageUrl() {
        return getStringValue("image_url");
    }
    
    public int getMembers() {
        return getIntegerValue("members");
    }
    
    public ArrayList<News> getNews() {
        if (articles == null) {
            setNews();
        }
        
        return articles;
    }
    
    public ArrayList<String> getPictures() {
        if (pictures == null) {
            setPictures();
        }
        
        return pictures;
    }
    
    public int getPopularity() {
        return getIntegerValue("popularity");
    }
    
    public int getRank() {
        return getIntegerValue("rank");
    }
    
    public JSONObject getRelated() {
        return getJsonValue("related");
    }
    
    public ArrayList<Review> getReviews() {
        if (reviews == null) {
            setReviews(1);
        }
        
        return reviews;
    }
    
    public double getScore() {
        return getDoubleValue("score");
    }
    
    public String getStatus() {
        return getStringValue("status");
    }
    
    public String getSynopsis() {
        return getStringValue("synopsis");
    }
    
    public String getTitle() {
        return getStringValue("title");
    }
    
    public String getType() {
        return getStringValue("type");
    }
    
    public String getUrl() {
        return getStringValue("url");
    }
    
    /**
     * Checks the boolean map for key. If map doesn't contain key, attempt to retrieve and update object
     */
    protected boolean getBooleanValue(String key) {
        if (!booleanProperties.containsKey(key)) {
            setDetails();
        }
        
        if (!booleanProperties.containsKey(key)) {
            return false;
        }
        
        return booleanProperties.get(key);
    }
    
    /**
     * Checks the double map for key. If map doesn't contain key, attempt to retrieve and update object
     */
    protected double getDoubleValue(String key) {
        if (!doubleProperties.containsKey(key)) {
            setDetails();
        }
        
        if (!doubleProperties.containsKey(key)) {
            return -1.0;
        }
        
        return doubleProperties.get(key);
    }
    
    /**
     * Checks the json map for key. If map doesn't contain key, attempt to retrieve and update object
     */
    protected JSONObject getJsonValue(String key) {
        if (!jsonProperties.containsKey(key)) {
            setDetails();
        }
        
        if (!jsonProperties.containsKey(key)) {
            return null;
        }
        
        return jsonProperties.get(key);
    }
    
    /**
     * Checks the integer map for key. If map doesn't contain key, attempt to retrieve and update object
     */
    protected int getIntegerValue(String key) {
        if (!integerProperties.containsKey(key)) {
            setDetails();
        }
        
        if (!integerProperties.containsKey(key)) {
            return -1;
        }
        
        
        return integerProperties.get(key);
    }
    
    /**
     * Checks the string map for key. If map doesn't contain key, attempt to retrieve and update object
     */
    protected String getStringValue(String key) {
        if (!stringProperties.containsKey(key)) {
            setDetails();
        }
        
        if (!stringProperties.containsKey(key)) {
            return null;
        }
        
        return stringProperties.get(key);
    }
    
    /**
     * Retrieve and update object values
     */
    protected boolean setDetails() {
        try {
            String result = MyAnimeList.makeAPICall("/" + media + "/" + id);
            setValues(result);
            return true;
        }
        catch (JSONException e) {
            // invalid id
            return false;
        }
    }
    
    protected boolean setNews() {
        ArrayList<News> articles;
        if (this.articles == null) {
            articles = new ArrayList<>();
        }
        else {
            articles = this.articles;
        }
        
        try {
            String jsonResponse;
            jsonResponse = MyAnimeList.makeAPICall("/" + media + "/" + id + "/news");
            
            JSONObject jObj = new JSONObject(jsonResponse);
            JSONArray jArray = jObj.getJSONArray("articles");
            int malId;
            String[] url;
            for (int i = 0; i < jArray.length(); i++) {
                url = jArray.getJSONObject(i).getString("url").split("/");
                malId = Integer.parseInt(url[url.length - 1]);
                News article = new News(malId);
                article.setValues(jArray.getJSONObject(i));
                articles.add(article);
            }
            
            this.articles = articles;
            return true;
        }
        catch (JSONException e) {
            return false;
        }
    }
    
    protected boolean setPictures() {
        ArrayList<String> pictures;
        if (this.pictures == null) {
            pictures = new ArrayList<String>();
        }
        else {
            pictures = this.pictures;
        }
        
        try {
            String jsonResponse;
            jsonResponse = MyAnimeList.makeAPICall("/" + media + "/" + id + "/pictures");

            JSONObject jObj = new JSONObject(jsonResponse);
            JSONArray jsonPictures = jObj.getJSONArray("pictures");
            for (int i = 0; i < jsonPictures.length(); i++) {
                pictures.add(jsonPictures.getJSONObject(i).getString("large"));
            }
            
            this.pictures = pictures;
            return true;
        }
        catch (JSONException e) {
            return false;
        }
    }
    
    protected boolean setReviews(int page) {
        ArrayList<Review> reviews;
        if (this.reviews == null) {
            reviews = new ArrayList<>();
        }
        else {
            reviews = this.reviews;
        }
        
        try {
            String jsonResponse;
            jsonResponse = MyAnimeList.makeAPICall("/" + media + "/" + id + "/reviews/" + Integer.toString(page));
            
            JSONObject jObj = new JSONObject(jsonResponse);
            JSONArray jArray = jObj.getJSONArray("reviews");
            int malId;
            for (int i = 0; i < jArray.length(); i++) {
                malId = jArray.getJSONObject(i).getInt("mal_id");
                Review review = new Review(malId);
                review.setValues(jArray.getJSONObject(i));
                reviews.add(review);
            }
            
            this.reviews = reviews;
            return true;
        }
        catch (JSONException e) {
            return false;
        }
    }

}
