package MyAnimeList.tests;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import MyAnimeList.Anime;
import MyAnimeList.Manga;
import MyAnimeList.MyAnimeList;
import MyAnimeList.User;

class MyAnimeListTest {

    @Test
    void testGetAnime() {
        int animeId = 21;
        Anime anime = MyAnimeList.getAnime(animeId);
        assertEquals(anime.getEnglishTitle(), "One Piece");
    }
    
    @Test
    void testGetUser() {
        String username = "Maxine";
        User user = MyAnimeList.getUser(username);
        assertEquals(user.getLocation(), "Baltimore, Maryland");
    }

    @Test
    void testGetTopAnime() {
        String topCategory = "TV";
        Map<Integer, Anime> topAnime = MyAnimeList.getTopAnime(topCategory);
        assertEquals(topAnime.get(9).getTitle(), "Gintama");
    }
       
    @Test
    void testGetTopManga() {
        String topCategory = "oneshots";
        Map<Integer, Manga> topManga = MyAnimeList.getTopManga(topCategory);
        assertEquals(topManga.get(4).getTitle(), "Tokidoki");
    }

    @Test
    void testSearchForAnimeByTitle() {
        String title = "Toaru Majutsu no Kinsho Mokuroku";
        TreeMap<Integer, Anime> results = MyAnimeList.searchForAnimeByTitle(title, 1);
        assertEquals(results.get(1).getEnglishTitle(), "A Certain Magical Index");
    }
    
    @Test
    void testSearchForMangaByTitle() {
        String title = "The Ghost in the Shell";
        TreeMap<Integer, Manga> results = MyAnimeList.searchForMangaByTitle(title, 1);
        assertEquals(results.get(1).getScore(), 8.11);
    }
}
