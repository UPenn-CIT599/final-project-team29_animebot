import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

class MyAnimeListTest {

    @Test
    void testGetAnimeInt() {
        int animeId = 21;
        Anime anime = MyAnimeList.getAnime(animeId);
        assertEquals(anime.getEnglishTitle(), "One Piece");
    }

    @Test
    void testGetTopAnime() {
        String topCategory = "TV";
        Map<Integer, Anime> topAnime = MyAnimeList.getTopAnime(topCategory);
        assertEquals(topAnime.get(9).getTitle(), "Gintama");
    }
    
    @Test
    void testGetUser() {
        String username = "Maxine";
        User user = MyAnimeList.getUser(username);
        assertEquals(user.getLocation(), "Baltimore, Maryland");
    }
    
    @Test
    void testGetTopManga() {
        String topCategory = "oneshots";
        Map<Integer, Manga> topManga = MyAnimeList.getTopManga(topCategory);
        assertEquals(topManga.get(4).getTitle(), "Tokidoki");
    }

}
