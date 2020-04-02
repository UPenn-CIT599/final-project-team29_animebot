package MyAnimeList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Manga profile on MyAnimeList
 */
public class Manga extends MyAnimeListMedia {
    private static Map<String, String> MANGA_PROPERTY_TYPES = new HashMap<>();
    static {
        MANGA_PROPERTY_TYPES.put("chapters", "integer");
        MANGA_PROPERTY_TYPES.put("publishing", "boolean");
        MANGA_PROPERTY_TYPES.put("scored_by", "integer");
        MANGA_PROPERTY_TYPES.put("volumes", "integer");
    }
    
    private TreeMap<Integer, Manga> recommendations;
    
    public Manga(int id) {
        super(id, "manga", MANGA_PROPERTY_TYPES);
    }
   
    /**
     * Gets the number of chapters in the manga
     * @return Number of chapters
     */
    public int getChapters() {
        return getIntegerValue("chapters");
    }
    
    /**
     * Gets list of user-submitted manga recommendations based on this manga
     * @return List of manga recommendations, sorted in descending order of recommendation count
     */
    public TreeMap<Integer, Manga> getRecommendations() {
        if (recommendations == null) {
            setRecommendations();
        }
        
        return recommendations;
    }
    
    /**
     * Gets the number of users who scored this manga
     * @return Number of scorers
     */
    public int getScoredBy() {
        return getIntegerValue("scored_by");
    }
    
    /**
     * Gets the number of volumes for this manga
     * @return Number of volumes
     */
    public int getVolumes() {
        return getIntegerValue("volumes");
    }
    
    /**
     * Gets the publishing status of this manga
     * @return True if still being published, False if not
     */
    public boolean isPublishing() {
        return getBooleanValue("publishing");
    }
    
    /**
     * Updates the recommendations based on this manga
     * @return True if recommendations is successfully updated, False if not
     */
    private boolean setRecommendations() {
        TreeMap<Integer, Manga> recommendations = new TreeMap<>();
        
        try {
            String jsonResponse = MyAnimeList.makeAPICall("/" + media + "/" + id + "/recommendations");
            JSONObject jObj = new JSONObject(jsonResponse);
            JSONArray jArray = jObj.getJSONArray("recommendations");
            
            Manga manga;
            for (int i = 0; i < jArray.length(); i++) {
                manga = MyAnimeList.getManga(jArray.getJSONObject(i));
                recommendations.put(i + 1, manga);
            }
            
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        this .recommendations = recommendations;
        return true;
    }
}
