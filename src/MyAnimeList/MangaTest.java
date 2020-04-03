package MyAnimeList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MangaTest {

    @Test
    void testGetPrequel() {
        Manga manga = MyAnimeList.getManga(23751);
        assertEquals(manga.getPrequel().get(0).getTitle(), "Monogatari Series: First Season");
    }

    @Test
    void testGetSequel() {
        Manga manga = MyAnimeList.getManga(23751);
        assertEquals(manga.getSequel().get(0).getTitle(), "Monogatari Series: Final Season");
    }

}
