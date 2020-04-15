package myanimelist;

import static org.junit.jupiter.api.Assertions.*;

import java.util.TreeMap;

import org.junit.jupiter.api.Test;

public class AnimeTest {

	@Test
	void testGetEpisodeList() {
		TreeMap<Integer, AnimeEpisode> episodes = MyAnimeList.getAnime(4181).getEpisodeList();
		assertEquals("The Goodbye at the End of Summer", episodes.get(1).getTitle());
	}

	@Test
	void testGetEpisodes() {
		Anime anime = MyAnimeList.getAnime(11061);
		assertEquals(148, anime.getEpisodes());
	}

	@Test
	void testGetPremiered() {
		Anime anime = MyAnimeList.getAnime(11061);
		assertEquals("Fall 2011", anime.getPremiered());
	}

	@Test
	void testGetPrequel() {
		Anime anime = MyAnimeList.getAnime(28977);
		assertEquals("Gintama Movie 2: Kanketsu-hen - Yorozuya yo Eien Nare", anime.getPrequel().get(0).getTitle());
	}

	@Test
	void testGetSequel() {
		Anime anime = MyAnimeList.getAnime(28977);
		assertEquals("Gintama.", anime.getSequel().get(0).getTitle());
	}

	@Test
	void testGetRecommendations() {
		Anime anime = MyAnimeList.getAnime(9253);
		assertEquals("Mirai Nikki", anime.getRecommendations().get(5).getTitle());
	}

	@Test
	void testGetSource() {
		Anime anime = MyAnimeList.getAnime(5114);
		assertEquals("Manga", anime.getSource());
	}

	@Test
	void testGetTrailerUrl() {
		Anime anime = MyAnimeList.getAnime(5114);
		assertEquals("https://www.youtube.com/embed/--IcmZkvL0Q?enablejsapi=1&wmode=opaque&autoplay=1",
				anime.getTrailerUrl());
	}

	@Test
	void testIsAiring() {
		Anime anime = MyAnimeList.getAnime(5114);
		assertEquals(false, anime.isAiring());
	}
}
