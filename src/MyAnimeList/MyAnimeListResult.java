package MyAnimeList;
import java.util.HashMap;
import java.util.Map;

/**
 * Search results from MyAnimeList
 */
public class MyAnimeListResult extends MyAnimeListObject {
    private static Map<String, String> SEARCH_RESULT_PROPERTY_TYPES = new HashMap<>();
    static {
        SEARCH_RESULT_PROPERTY_TYPES.put("image_url", "string");
        SEARCH_RESULT_PROPERTY_TYPES.put("mal_id", "string");
        SEARCH_RESULT_PROPERTY_TYPES.put("synopsis", "string");
        SEARCH_RESULT_PROPERTY_TYPES.put("title", "string");
        SEARCH_RESULT_PROPERTY_TYPES.put("type", "string");
        SEARCH_RESULT_PROPERTY_TYPES.put("url", "string");
    }
    
    protected String media;
    
    public MyAnimeListResult(int id, String mediaType) {
        super(id, SEARCH_RESULT_PROPERTY_TYPES);
        media = mediaType;
    }
    
    public MyAnimeListResult(int id, String mediaType, Map<String, String> additionalPropertyTypes) {
        this(id, mediaType);
        propertyTypes.putAll(additionalPropertyTypes);
    }
    
    /**
     * Gets the Image url for this search result
     * @return Image url
     */
    public String getImageUrl() {
        return getStringValue("image_url");
    }
    
    /**
     * Gets the synopsis for this search result
     * @return Synopsis
     */
    public String getSynopsis() {
        return getStringValue("synopsis");
    }
    
    /**
     * Gets the title of this search result
     * @return Title
     */
    public String getTitle() {
        return getStringValue("title");
    }
    
    /**
     * Gets the media type of this search result
     * @return Media type
     */
    public String getType() {
        return getStringValue("type");
    }
    
    /**
     * Gets the profile url to this search result
     * @return Profile url
     */
    public String getUrl() {
        return getStringValue("url");
    }
}
