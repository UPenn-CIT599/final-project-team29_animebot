package myanimelist;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MyAnimeListMediaTest {
	@Test
	void testGetBackground() {
		Anime anime = MyAnimeList.getAnime(9253);
		assertEquals("Steins;Gate is based on 5pb. and Nitroplus' .", anime.getBackground());
	}

	@Test
	void testGetEnglishTitle() {
		Anime anime = MyAnimeList.getAnime(5114);
		assertEquals("Fullmetal Alchemist: Brotherhood", anime.getEnglishTitle());
	}
	
	@Test
	void testGetFavorites() {
		Anime anime = MyAnimeList.getAnime(5114);
		assertEquals(147,901, anime.getFavorites());
	}
	
	@Test
	void testGetImageUrl() {
		Anime anime = MyAnimeList.getAnime(28977);
		assertEquals("https://cdn.myanimelist.net/images/anime/3/72078.jpg", anime.getImageUrl());
	}
	
	@Test
	void testGetMembers() {
		Anime anime = MyAnimeList.getAnime(28977);
		assertEquals(301,625, anime.getMembers());
	}
	
	@Test
	void testGetNews() {
		Anime anime = MyAnimeList.getAnime(11061);
		assertEquals("North American Anime & Manga Releases for February", anime.getNews().get(0).toString());
	}

	
	@Test
	void testGetPictures() {
	}
	
	@Test
	void testGetPopularity() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals(35, anime.getPopularity());
	}
	
	@Test
	void testGetRank() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals(110, anime.getRank());
	}
	
	
}

