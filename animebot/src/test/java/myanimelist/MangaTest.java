package myanimelist;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MangaTest {

	@Test
	void testgetChapters() {
		Manga manga = MyAnimeList.getManga(3468);
		assertEquals(62, manga.getChapters());
	}

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
	void testGetRecommendations() {
		Manga manga = MyAnimeList.getManga(16765);
		assertEquals("The Ravages of Time", manga.getRecommendations().get(3).getTitle());
	}

	@Test
	void testGetScoreBy() {
		Manga manga = MyAnimeList.getManga(25);
		assertEquals(106,455, manga.getScoredBy());
	}

	@Test
	void testGetVolumes() {
		Manga manga = MyAnimeList.getManga(1706);
		assertEquals(24, manga.getVolumes());
	}

	@Test
	void testGetIsPublishing() {
		Manga manga = MyAnimeList.getManga(13);
		assertEquals(true, manga.isPublishing());
	}
}
