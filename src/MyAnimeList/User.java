package MyAnimeList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * User profile on MyAnimeList
 */
public class User extends MyAnimeListObject {
    private static Map<String, String> USER_PROPERTY_TYPES = new HashMap<>();
    static {
        USER_PROPERTY_TYPES.put("about", "string");
        USER_PROPERTY_TYPES.put("gender", "string");
        USER_PROPERTY_TYPES.put("image_url", "string");
        USER_PROPERTY_TYPES.put("location", "string");
        USER_PROPERTY_TYPES.put("url", "string");
        USER_PROPERTY_TYPES.put("user_id", "integer");
        USER_PROPERTY_TYPES.put("username", "string");
    }
    
    private ArrayList<Anime> animeList;
    private ArrayList<Manga> mangaList;
    
    public User(int id) {
        super(id, USER_PROPERTY_TYPES);
    }
    
    /**
     * Get about information of the user
     * @return About information
     */
    public String getAbout() {
        return getStringValue("about");
    }
    
    /**
     * Gets the list of anime that the user has added to their profile
     * @return List of anime
     */
    public ArrayList<Anime> getAnimeList() {
        if (animeList == null) {
            setAnimeList();
        }
        
        return animeList;
    }
    
    /**
     * Gets the gender of the user
     * @return Gender
     */
    public String getGender() {
        return getStringValue("gender");
    }
    
    /**
     * Gets the profile picture url of the user
     * @return Profile picture url
     */
    public String getImageUrl() {
        return getStringValue("image_url");
    }
    
    /**
     * Gets the location of the user
     * @return User location
     */
    public String getLocation() {
        return getStringValue("location");
    }
    
    /**
     * Gets the list of manga that the user has added to their profile
     * @return List of manga
     */
    public ArrayList<Manga> getMangaList() {
        if (mangaList == null) {
            setMangaList();
        }
        
        return mangaList;
    }
    
    /**
     * Gets the url to the user's profile
     * @return User profile url
     */
    public String getUrl() {
        return getStringValue("url");
    }
    
    /**
     * Gets the user's username
     * @return Username
     */
    public String getUsername() {
        return getStringValue("username");
    }
   
    /**
     * Updates the user's anime list
     * @return True if update is successful, false if not
     */
    private boolean setAnimeList() {
        ArrayList<Anime> animeList;
        if (this.animeList == null) {
            animeList = new ArrayList<>();
        }
        else {
            animeList = this.animeList;
        }
        
        try {
            String jsonResponse;
            jsonResponse = MyAnimeList.makeAPICall("/user/" + getUsername() + "/animelist");
            
            JSONObject jObj = new JSONObject(jsonResponse);
            JSONArray jArray = jObj.getJSONArray("anime");
            
            Anime anime;
            for (int i = 0; i < jArray.length(); i++) {
                anime = MyAnimeList.getAnime(jArray.getJSONObject(i));
                animeList.add(anime);
            }
            
            this.animeList = animeList;
            return true;
        }
        catch (JSONException e) {
            return false;
        }
    }
    
    /**
     * Update's the user's profile
     * @param username The user's username
     * @return True if update is successful, false if not
     */
    public boolean setDetails(String username) {
        String result = MyAnimeList.makeAPICall("/user/" + username);
        setValues(result);
        id = getIntegerValue("user_id");
        return true;
    }
    
    /**
     * Updates the user's manga list
     * @return True if update is successful, false if not
     */
    private boolean setMangaList() {
        ArrayList<Manga> mangaList;
        if (this.mangaList == null) {
            mangaList = new ArrayList<>();
        }
        else {
            mangaList = this.mangaList;
        }
        
        try {
            String jsonResponse;
            jsonResponse = MyAnimeList.makeAPICall("/user/" + getUsername() + "/mangalist");
            
            JSONObject jObj = new JSONObject(jsonResponse);
            JSONArray jArray = jObj.getJSONArray("manga");
            
            Manga manga;
            for (int i = 0; i < jArray.length(); i++) {
                manga = MyAnimeList.getManga(jArray.getJSONObject(i));
                mangaList.add(manga);
            }
            
            this.mangaList = mangaList;
            return true;
        }
        catch (JSONException e) {
            return false;
        }
    }
}
