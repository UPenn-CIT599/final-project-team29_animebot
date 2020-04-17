package myanimelist;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Anime profile on MyAnimeList 
 */
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
    
    /**
     * Gets list of related anime
     * @param relation Relation to the anime: Prequel or Sequel
     * @return List of related anime for relation
     */
    private ArrayList<Anime> findRelatedAnime(String relation) {
        ArrayList<Anime> relatedAnimes = new ArrayList<>();
        if (!getRelated().has(relation)) {
            return relatedAnimes;
        }
        
        try {
            JSONArray jArray = getRelated().getJSONArray(relation);
            
            for (int i = 0; i < jArray.length(); i++) {
                relatedAnimes.add(MyAnimeList.getAnime(getRelated().getJSONArray(relation).getJSONObject(i)));
            }
            
            return relatedAnimes;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return relatedAnimes;
    }
    
    /**
     * Gets list of episodes for the anime
     * @return List of episodes, sorted by ascending episode order
     */
    public TreeMap<Integer, AnimeEpisode> getEpisodeList() {
        if (episodeList == null) {
            setEpisodes();
        }
        
        return episodeList;
    }
    
    /**
     * Gets number of episodes for the anime
     * @return Number of episodes
     */
    public int getEpisodes() {
        return getIntegerValue("episodes");
    }
    
    /**
     * Gets season and year the anime premiered
     * @return Season and year of anime premier
     */
    public String getPremiered() {
        return getStringValue("premiered");
    }
    
    /**
     * Gets list of prequels for this anime
     * @return List of prequels
     */
    public ArrayList<Anime> getPrequel() {
        if (prequel == null) {
            prequel = findRelatedAnime("Prequel");
        }
        
        return prequel;
    }
    
    /**
     * Gets list of user-submitted anime recommendations based on this anime
     * @return List of anime recommendations, sorted in descending order of recommendation count
     */
    public TreeMap<Integer, Anime> getRecommendations() {
        if (recommendations == null) {
            setRecommendations();
        }
        
        return recommendations;
    }
        
    /**
     * Gets list of sequels for this anime
     * @return List of sequels
     */
    public ArrayList<Anime> getSequel() {
        if (sequel == null) {
            sequel = findRelatedAnime("Sequel");
        }
        
        return sequel;
    }
    
    /**
     * Gets the original source/inspiration for the anime
     * @return Source of the anime
     */
    public String getSource() {
        return getStringValue("source");
    }
    
    /**
     * Gets the trailer video url for the anime 
     * @return Trailer video url
     */
    public String getTrailerUrl() {
        return getStringValue("trailer_url");
    }
    
    /**
     * Gets the airing status for the anime
     * @return True if still airing, False if not
     */
    public boolean isAiring() {
        return getBooleanValue("airing");
    }
    
    /**
     * Updates the episode list for this anime
     * @return True if episode list is successfully updated, False if not
     */
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
                    episodes.put(malId, episode);
                }
                
                page++;
            } while (page <= last_page);
            
            this.episodeList = episodes;
            return true;
        }
        catch (JSONException e) {
            return false;
        }
    }
    
    /**
     * Updates the recommendations based on this anime
     * @return True if recommendations is successfully updated, False if not
     */
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
            
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        this.recommendations = recommendations;
        return true;
    }
}
