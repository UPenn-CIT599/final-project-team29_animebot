import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyAnimeList {
    private static String ENDPOINT = "https://api.jikan.moe/v3"; 
    
    private static String[] ANIME_TOP_CATEGORIES = new String[]{"airing","upcoming","tv","movie","ova","special","bypopularity","favorite"};
    private static String[] MANGA_TOP_CATEGORIES = new String[]{"manga","novels","oneshots","doujin","manhwa","manhua","bypopularity","favorite"};
    
    private static Map<Integer, Anime> animeList = new HashMap<>();
    private static Map<Integer, Manga> mangaList = new HashMap<>();
    private static Map<String, User> userList = new HashMap<>();
    
    /**
     * Gets an anime's profile
     * @param id MyAnimeList id of anime 
     * @return MyAnimeList anime profile
     */
    public static Anime getAnime(int id) {
        if (animeList.containsKey(id)) {
            return animeList.get(id);
        }

        Anime anime = new Anime(id);
        if (anime.setDetails()) {
            animeList.put(id, anime);
            return anime;
        }
        else {
            // invalid id
            return null;
        }
    }
    
    /**
     * Gets an anime's profile
     * @param jsonResponse Partial or complete JSON details about the anime from MyAnimeList.makeAPICall
     * @return Partial or complete anime profile
     * @throws JSONException 
     */
    public static Anime getAnime(JSONObject resp) throws JSONException {
        int id = resp.getInt("mal_id");
        
        if (animeList.containsKey(id)) {
            return animeList.get(id);
        }

        Anime anime = new Anime(id);
        anime.setValues(resp);
        animeList.put(id, anime);
        return anime;
    }
    
    /**
     * Gets a manga's profile
     * @param id MyAnimeList id of manga 
     * @return MyAnimeList manga profile
     */
    public static Manga getManga(int id) {
        if (mangaList.containsKey(id)) {
            return mangaList.get(id);
        }

        Manga manga = new Manga(id);
        if (manga.setDetails()) {
            mangaList.put(id, manga);
            return manga;
        }
        else {
            // invalid id
            return null;
        }
    }
    
    /**
     * Gets a manga's profile
     * @param jsonResponse Partial or complete JSON details about the manga from MyAnimeList.makeAPICall
     * @return Partial or complete manga profile
     * @throws JSONException 
     */
    public static Manga getManga(JSONObject resp) throws JSONException {
        int id = resp.getInt("mal_id");
        
        if (mangaList.containsKey(id)) {
            return mangaList.get(id);
        }
        
        Manga manga = new Manga(id);
        manga.setValues(resp);
        mangaList.put(id, manga);
        return manga;
    }
    
    /**
     * Gets top 50 anime in the category, according to MyAnimeList
     * @param category Top category to determine ordered list
     * @return Top 50 anime in the category, ordered by rank
     */
    public static TreeMap<Integer, Anime> getTopAnime(String category) {
        String categoryStr = category;
        if (categoryStr == null || !isValueInArray(categoryStr, ANIME_TOP_CATEGORIES)) {
            categoryStr = "";
        }
        
        TreeMap<Integer, Anime> topAnime = new TreeMap<>();
        
        try {
            String jsonResponse = makeAPICall("/top/anime/1/" + categoryStr.toLowerCase());
            JSONObject jObj = new JSONObject(jsonResponse);
            JSONArray jArray = jObj.getJSONArray("top");
            
            int malId, rank;
            JSONObject obj;
            Anime anime;
            for (int i = 0; i < jArray.length(); i++) {
                obj = jArray.getJSONObject(i);
                malId = obj.getInt("mal_id");
                rank = obj.getInt("rank");
                
                if (animeList.containsKey(malId)) {
                    anime = animeList.get(malId);
                }
                else {
                    anime = new Anime(malId);
                    anime.setValues(obj);
                    animeList.put(malId, anime);
                }
                
                topAnime.put(rank, anime);
            }
            
        } catch (IOException | JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return topAnime;
    }
    
    /**
     * Gets top 50 manga in the category, according to MyAnimeList
     * @param category Top category to determine ordered list
     * @return Top 50 manga in category, ordered by rank
     */
    public static TreeMap<Integer, Manga> getTopManga(String category) {
        String categoryStr = category;
        if (categoryStr == null || !isValueInArray(categoryStr, MANGA_TOP_CATEGORIES)) {
            categoryStr = "bypopularity";
        }
        
        TreeMap<Integer, Manga> topManga = new TreeMap<>();
        
        try {
            String jsonResponse = makeAPICall("/top/manga/1/" + categoryStr.toLowerCase());
            JSONObject jObj = new JSONObject(jsonResponse);
            JSONArray jArray = jObj.getJSONArray("top");
            
            int malId, rank;
            JSONObject obj;
            Manga manga;
            for (int i = 0; i < jArray.length(); i++) {
                obj = jArray.getJSONObject(i);
                malId = obj.getInt("mal_id");
                rank = obj.getInt("rank");
                
                if (mangaList.containsKey(malId)) {
                    manga = mangaList.get(malId);
                }
                else {
                    manga = new Manga(malId);
                    manga.setValues(obj);
                    mangaList.put(malId, manga);
                }
                
                topManga.put(rank, manga);
            }
            
        } catch (IOException | JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return topManga;
    }
    
    /**
     * Gets a MyAnimeList user profile by their username
     * @param username Username of the MyAnimeList user
     * @return MyAnimeList user profile
     */
    public static User getUser(String username) {
        if (userList.containsKey(username)) {
            return userList.get(username);
        }

        User user = new User(-1); // Dummy user_id will be replaced when found
        
        if (user.setDetails(username)) {
            userList.put(username, user);
            return user;
        }
        else {
            // invalid id
            return null;
        }
    }
    
    /**
     * Checks if a value is in the array, case insensitive 
     * @param value Value to look for in array
     * @param array Array of values
     * @return True if value is in the array, False if not
     */
    private static boolean isValueInArray(String value, String[] array) {
        for (String val : array) {
            if (value.equalsIgnoreCase(val)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Performs a HTTP GET request to the MyAnimeList API
     * @param query Request path
     * @return JSON response as a String
     * @throws IOException
     */
    public static String makeAPICall(String query) throws IOException {
        URL restCall = new URL(ENDPOINT + query);
        URLConnection yc = restCall.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
             response.append(inputLine);
        }
        in.close();
             
        return response.toString();
    }
}
