package myanimelist;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Base object for MyAnimeList media types (ie Anime, Manga)
 */
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
    
    /**
     * Gets this media's background
     * @return Background
     */
    public String getBackground() {
        return getStringValue("background").toString();
    }
    
    /**
     * Gets the English title for this media
     * @return English title
     */
    public String getEnglishTitle() {
        return getStringValue("title_english").toString();
    }
    
    /**
     * Gets the number of users who favorited this media
     * @return Number of users
     */
    public int getFavorites() {
        return getIntegerValue("favorites");
    }
    
    /**
     * Gets the url of the image for this media
     * @return Image Url
     */
    public String getImageUrl() {
        return getStringValue("image_url");
    }
    
    /**
     * Gets the number of users who added this media to their profile
     * @return Number of users
     */
    public int getMembers() {
        return getIntegerValue("members");
    }
    
    /**
     * Gets a list of news articles related to this media
     * @return List of news articles
     */
    public ArrayList<News> getNews() {
        if (articles == null) {
            setNews();
        }
        
        return articles;
    }
    
    /**
     * Gets a list of image url's related to this media
     * @return List of Image Url's
     */
    public ArrayList<String> getPictures() {
        if (pictures == null) {
            setPictures();
        }
        
        return pictures;
    }
    
    /**
     * Gets the popularity rank for this media
     * @return Popularity ranking
     */
    public int getPopularity() {
        return getIntegerValue("popularity");
    }
    
    /**
     * Gets the overall rank for this media
     * @return Ranking
     */
    public int getRank() {
        return getIntegerValue("rank");
    }
    
    /**
     * Gets related media items for this media
     * @return Related media
     */
    public JSONObject getRelated() {
        return getJsonValue("related");
    }
    
    /**
     * Gets list of reviews for this media
     * @return List of reviews
     */
    public ArrayList<Review> getReviews() {
        if (reviews == null) {
            setReviews(1);
        }
        
        return reviews;
    }
    
    /**
     * Gets average user score for this media
     * @return User score
     */
    public double getScore() {
        return getDoubleValue("score");
    }
    
    /**
     * Gets the overall status of the media (ie Currently Airing, Finished)
     * @return Status
     */
    public String getStatus() {
        return getStringValue("status");
    }
    
    /**
     * Gets the synopsis of the media
     * @return Synopsis
     */
    public String getSynopsis() {
        return getStringValue("synopsis");
    }
    
    /**
     * Gets the title of the media
     * @return Title
     */
    public String getTitle() {
        return getStringValue("title");
    }
    
    /**
     * Gets the media type (ie Anime, Manga)
     * @return Media type
     */
    public String getType() {
        return getStringValue("type");
    }
    
    /**
     * Gets the MyAnimeList profile url for the media
     * @return Media profile url
     */
    public String getUrl() {
        return getStringValue("url");
    }
    
    /**
     * Checks the boolean map for key. If map doesn't contain key, attempt to retrieve and update object
     * @return Boolean value for the key
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
     * @return Double value for the key
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
     * @return JSONObject value for the key
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
     * @return Integer value for the key
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
     * @return String value for the key
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
     * @return True if details were successfully updated, False if not
     */
    protected boolean setDetails() {
        String result = MyAnimeList.makeAPICall("/" + media + "/" + id);
        setValues(result);
        return true;
    }
    
    /**
     * Updates the list of news articles for this media
     * @return True if news articles were updated, False if not
     */
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
    
    /**
     * Updates the list of picture url's related to this media
     * @return True if picture url's were updated, False if not
     */
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
    
    /**
     * Updates the list of reviews for this media
     * @param page Page number of review
     * @return True if reviews were updated, False if not
     */
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
