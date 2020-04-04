package myanimelist;
import java.util.HashMap;
import java.util.Map;

/**
 * Anime episode on MyAnimeList
 */
public class AnimeEpisode extends MyAnimeListObject {
    private static Map<String, String> ANIME_EPISODE_PROPERTY_TYPES = new HashMap<>();
    static {
        ANIME_EPISODE_PROPERTY_TYPES.put("anime_id", "integer");
        ANIME_EPISODE_PROPERTY_TYPES.put("episode_id", "integer");
        ANIME_EPISODE_PROPERTY_TYPES.put("filler", "boolean");
        ANIME_EPISODE_PROPERTY_TYPES.put("forum_url", "string");
        ANIME_EPISODE_PROPERTY_TYPES.put("recap", "boolean");
        ANIME_EPISODE_PROPERTY_TYPES.put("title", "string");
        ANIME_EPISODE_PROPERTY_TYPES.put("title_romanji", "string");
        ANIME_EPISODE_PROPERTY_TYPES.put("video_url", "string");
    }
    
    public AnimeEpisode(int id) {
        super(id, ANIME_EPISODE_PROPERTY_TYPES);
    }
    
    /**
     * Gets the id of the anime this episode belongs to
     * @return Id of the anime for this episode
     */
    public int getAnimeId() {
        return getIntegerValue("anime_id");
    }

    /**
     * Gets the title of this episode
     * @return Title of the episode
     */
    public String getTitle() {
        return getStringValue("title");
    }
    
    /**
     * Gets the Romanji title of this episode
     * @return Romanji title of the episode
     */
    public String getTitleRomanji() {
        return getStringValue("title_romanji");
    }
    
    /**
     * Gets the URL for the video preview of this episode
     * @return URL of the video preview
     */
    public String getVideoUrl() {
        return getStringValue("video_url");
    }
    
    /**
     * Gets the URL for the forum thread discussing this episode
     * @return URL for the forum thread for this episode
     */
    public String getForumUrl() {
        return getStringValue("forum_url");
    }
    
    /**
     * Determines whether or not this episode contributes to the anime's storyline
     * @return True if is a filler episode, False if not
     */
    public boolean isFiller() {
        return getBooleanValue("filler");
    }
    
    /**
     * Determines whether or not this episode is a recap of a previous episode
     * @return True if is a recap episode, False if not
     */
    public boolean isRecap() {
        return getBooleanValue("recap");
    }
}
