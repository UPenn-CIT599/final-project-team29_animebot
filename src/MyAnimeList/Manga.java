package MyAnimeList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
   
    public int getChapters() {
        return getIntegerValue("chapters");
    }
    
    public TreeMap<Integer, Manga> getRecommendations() {
        if (recommendations == null) {
            setRecommendations();
        }
        
        return recommendations;
    }
    
    public double getScoredBy() {
        return getDoubleValue("scored_by");
    }
    
    public int getVolumes() {
        return getIntegerValue("volumes");
    }
    
    public boolean isPublishing() {
        return getBooleanValue("publishing");
    }
    
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
