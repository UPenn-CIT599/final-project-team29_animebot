package MyAnimeList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Anime extends MyAnimeListMedia {
    private static Map<String, String> ANIME_PROPERTY_TYPES = new HashMap<>();
    static {
        ANIME_PROPERTY_TYPES.put("airing", "boolean");
        ANIME_PROPERTY_TYPES.put("episodes", "integer");
        ANIME_PROPERTY_TYPES.put("premiered", "string");
        ANIME_PROPERTY_TYPES.put("source", "string");
        ANIME_PROPERTY_TYPES.put("trailer_url", "string");
    }
    
    private TreeMap<Integer, AnimeEpisode> episodeList;
    private ArrayList<Anime> prequel, sequel;
    private TreeMap<Integer, Anime> recommendations;
    
    public Anime(int id) {
        super(id, "anime", ANIME_PROPERTY_TYPES);
    }
    
    private ArrayList<Anime> findRelatedAnime(String relation) {
        if (!getRelated().has(relation)) {
            return null;
        }
        
        try {
            ArrayList<Anime> relatedAnimes = new ArrayList<>();
            JSONArray jArray = getRelated().getJSONArray(relation);
            
            for (int i = 0; i < jArray.length(); i++) {
                relatedAnimes.add(MyAnimeList.getAnime(getRelated().getJSONArray(relation).getJSONObject(i)));
            }
            
            return relatedAnimes;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }
    
    public TreeMap<Integer, AnimeEpisode> getEpisodeList() {
        if (episodeList == null) {
            setEpisodes();
        }
        
        return episodeList;
    }
    
    public int getEpisodes() {
        return getIntegerValue("episodes");
    }
    
    public String getPremiered() {
        return getStringValue("premiered");
    }
    
    public ArrayList<Anime> getPrequel() {
        if (prequel == null) {
            if (!getRelated().has("Prequel")){
                return null;
            }
            
            prequel = findRelatedAnime("Prequel");
        }
        
        return prequel;
    }
    
    public TreeMap<Integer, Anime> getRecommendations() {
        if (recommendations == null) {
            setRecommendations();
        }
        
        return recommendations;
    }
        
    public ArrayList<Anime> getSequel() {
        if (sequel == null) {
            if (!getRelated().has("Sequel")){
                return null;
            }
            
            sequel = findRelatedAnime("Sequel");
        }
        
        return sequel;
    }
    
    public String getSource() {
        return getStringValue("source");
    }
    
    public String getTrailerUrl() {
        return getStringValue("trailer_url");
    }
    
    public boolean isAiring() {
        return getBooleanValue("airing");
    }
    
    private boolean setEpisodes() {
        int animeId = getId();
        
        TreeMap<Integer, AnimeEpisode> episodes = new TreeMap<>();
        
        try {
            int page = 1, last_page = 1, malId;
            String jsonResponse;
            JSONObject jObj, obj;
            JSONArray jArray;
            do {
                jsonResponse = MyAnimeList.makeAPICall("/anime/" + animeId + "/episodes/" + Integer.toString(page));
                
                jObj = new JSONObject(jsonResponse);
                last_page = jObj.getInt("episodes_last_page");
                jArray = jObj.getJSONArray("episodes");

                for (int i = 0; i < jArray.length(); i++) {
                    obj = jArray.getJSONObject(i);
                    malId = obj.getInt("episode_id");
                    
                    AnimeEpisode episode = new AnimeEpisode(malId);
                    episode.setValues(obj);
                    episodes.put(id, episode);
                }
                
                page++;
            } while (page <= last_page);
            
            this.episodeList = episodes;
            return true;
        }
        catch (IOException | JSONException e) {
            return false;
        }
    }
    
    private boolean setRecommendations() {
        TreeMap<Integer, Anime> recommendations = new TreeMap<>();
        
        try {
            String jsonResponse = MyAnimeList.makeAPICall("/" + media + "/" + id + "/recommendations");
            JSONObject jObj = new JSONObject(jsonResponse);
            JSONArray jArray = jObj.getJSONArray("recommendations");
            
            Anime anime;
            for (int i = 0; i < jArray.length(); i++) {
                anime = MyAnimeList.getAnime((jArray.getJSONObject(i)));
                recommendations.put(i + 1, anime);
            }
            
        } catch (IOException | JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        this.recommendations = recommendations;
        return true;
    }
}
