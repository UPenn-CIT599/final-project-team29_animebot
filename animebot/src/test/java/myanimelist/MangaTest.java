package myanimelist;

import static org.junit.jupiter.api.Assertions.*;
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

}
