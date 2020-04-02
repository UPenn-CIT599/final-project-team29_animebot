package MyAnimeList;
import java.util.HashMap;
import java.util.Map;

/**
 * News articles on MyAnimeList
 */
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
    
    /**
     * Gets the author's name of this news article
     * @return Author's name
     */
    public String getAuthorName() {
        return getStringValue("author_name");
    }
    
    /**
     * Gets the user profile url of the author
     * @return User profile url
     */
    public String getAuthorUrl() {
        return getStringValue("author_url");
    }
    
    /**
     * Gets the number of comments for this news article
     * @return Number of comments
     */
    public int getComments() {
        return getIntegerValue("comments");
    }
    
    /**
     * Gets the forum url discussing this news article
     * @return Forum url
     */
    public String getForumUrl() {
        return getStringValue("forum_url");
    }
    
    /**
     * Gets the image url for this news article
     * @return Image url
     */
    public String getImageUrl() {
        return getStringValue("image_url");
    }
    
    /**
     * Gets the introduction to this news article
     * @return Introduction
     */
    public String getIntro() {
        return getStringValue("intro");
    }
    
    /**
     * Gets the url to this news article
     * @return Url
     */
    public String getUrl() {
        return getStringValue("url");
    }
    
    /**
     * Gets the title of this news article
     * @return Title
     */
    public String getTitle() {
        return getStringValue("title");
    }
}