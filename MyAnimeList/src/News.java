import java.util.HashMap;
import java.util.Map;

public class News extends MyAnimeListObject {
    private static Map<String, String> NEWS_PROPERTY_TYPES = new HashMap<>();
    static {
        NEWS_PROPERTY_TYPES.put("author_name", "string");
        NEWS_PROPERTY_TYPES.put("author_url", "string");
        NEWS_PROPERTY_TYPES.put("comments", "integer");
        NEWS_PROPERTY_TYPES.put("forum_url", "string");
        NEWS_PROPERTY_TYPES.put("image_url", "string");
        NEWS_PROPERTY_TYPES.put("intro", "string");
        NEWS_PROPERTY_TYPES.put("title", "string");
        NEWS_PROPERTY_TYPES.put("url", "string");
    }
    
    public News(int id) {
        super(id, NEWS_PROPERTY_TYPES);
    }
    
    public String getAuthorName() {
        return getStringValue("author_name");
    }
    
    public String getAuthorUrl() {
        return getStringValue("author_url");
    }
    
    public int getComments() {
        return getIntegerValue("comments");
    }
    
    public String getForumUrl() {
        return getStringValue("forum_url");
    }
    
    public String getImageUrl() {
        return getStringValue("image_url");
    }
    
    public String getIntro() {
        return getStringValue("intro");
    }
    
    public String getUrl() {
        return getStringValue("url");
    }
    
    public String getTitle() {
        return getStringValue("title");
    }
}