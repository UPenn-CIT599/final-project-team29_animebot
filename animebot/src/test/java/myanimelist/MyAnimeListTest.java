package myanimelist;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

class MyAnimeListTest {

    @Test
    void testGetAnime() {
        int animeId = 21;
        Anime anime = MyAnimeList.getAnime(animeId);
        assertEquals("One Piece", anime.getEnglishTitle());
    }
    
    @Test
    void testGetUser() {
        String username = "Maxine";
        User user = MyAnimeList.getUser(username);
        assertEquals("Baltimore, Maryland", user.getLocation());
    }

    @Test
    void testGetTopAnime() {
        // subject to change
        String topCategory = "TV";
        Map<Integer, Anime> topAnime = MyAnimeList.getTopAnime(topCategory);
        assertEquals("Gintama.", topAnime.get(9).getTitle());
    }
       
    @Test
    void testGetTopManga() {
        // subject to change
        String topCategory = "oneshots";
        Map<Integer, Manga> topManga = MyAnimeList.getTopManga(topCategory);
        assertEquals("Tokidoki", topManga.get(4).getTitle());
    }

    @Test
    void testSearchForAnimeByTitle() {
        String title = "Toaru Majutsu no Kinsho Mokuroku";
        TreeMap<Integer, Anime> results = MyAnimeList.searchForAnimeByTitle(title, 1);
        assertEquals("A Certain Magical Index", results.get(1).getEnglishTitle());
    }
    
    @Test
    void testSearchForMangaByTitle() {
        String title = "The Ghost in the Shell";
        TreeMap<Integer, Manga> results = MyAnimeList.searchForMangaByTitle(title, 1);
        assertEquals(8.11, results.get(1).getScore());
    }
}
