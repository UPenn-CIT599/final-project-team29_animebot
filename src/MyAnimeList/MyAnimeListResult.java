package MyAnimeList;
import java.util.HashMap;
import java.util.Map;

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
    
    public String getImageUrl() {
        return getStringValue("image_url");
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
}
