package myanimelist;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

class MangaTest {

    @Test
    void testGetPrequel() {
        Manga manga = MyAnimeList.getManga(23751);
        assertEquals("Monogatari Series: First Season", manga.getPrequel().get(0).getTitle());
    }

    @Test
    void testGetSequel() {
        Manga manga = MyAnimeList.getManga(23751);
        assertEquals("Monogatari Series: Final Season", manga.getSequel().get(0).getTitle());
    }
    
    @Test
    void testCompareTo() {
        Map<Integer, Manga> topManga = MyAnimeList.getTopManga("manga");
        Manga manga2 = topManga.get(2);
        Manga manga4 = topManga.get(4);
        
        assertEquals(-1, manga4.compareTo(manga2));
    }

}
