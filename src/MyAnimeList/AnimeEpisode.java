package MyAnimeList;
import java.util.HashMap;
import java.util.Map;

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
     * @return the animeId
     */
    public int getAnimeId() {
        return getIntegerValue("anime_id");
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return getStringValue("title");
    }
    /**
     * @return the titleRomanji
     */
    public String getTitleRomanji() {
        return getStringValue("title_romanji");
    }
    /**
     * @return the videoUrl
     */
    public String getVideoUrl() {
        return getStringValue("video_url");
    }
    /**
     * @return the forumUrl
     */
    public String getForumUrl() {
        return getStringValue("forum_url");
    }
    /**
     * @return the filler
     */
    public boolean isFiller() {
        return getBooleanValue("filler");
    }
    /**
     * @return the recap
     */
    public boolean isRecap() {
        return getBooleanValue("recap");
    }
}
