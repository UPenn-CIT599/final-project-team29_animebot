package myanimelist;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;

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
		assertEquals(147, 901, anime.getFavorites());
	}

	@Test
	void testGetImageUrl() {
		Anime anime = MyAnimeList.getAnime(28977);
		assertEquals("https://cdn.myanimelist.net/images/anime/3/72078.jpg", anime.getImageUrl());
	}

	@Test
	void testGetMembers() {
		Anime anime = MyAnimeList.getAnime(28977);
		assertEquals(301, 625, anime.getMembers());
	}

	@Test
	void testGetNews() {
		Anime anime = MyAnimeList.getAnime(11061);
		assertEquals("North American Anime & Manga Releases for February", anime.getNews().get(0).getTitle());
	}

	@Test
	void testGetPictures() {
		Anime anime = MyAnimeList.getAnime(11061);
		assertEquals("https://cdn.myanimelist.net/images/anime/11/33657l.jpg", anime.getPictures().get(0));
	}

	@Test
	void testGetPopularity() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals(33, anime.getPopularity());
	}

	@Test
	void testGetRank() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals(110, anime.getRank());
	}

	@Test
	void testGetRelated() {
		JSONObject jObj = new JSONObject();
		jObj.put("mal_id", 813);
		Anime anime = MyAnimeList.getAnime(jObj);
		assertEquals("Dragon Ball Z", anime.getTitle());
	}

	@Test
	void testGetReviews() {
		Anime anime = MyAnimeList.getAnime(5114);
		assertEquals("tazillo", anime.getReviews().get(0).getReviewer().getUsername());
	}

	@Test
	void testGetScore() {
		Anime anime = MyAnimeList.getAnime(5114);
		assertEquals(9.23, anime.getScore());
	}

	@Test
	void testGetStatus() {
		Anime anime = MyAnimeList.getAnime(5114);
		assertEquals("Finished Airing", anime.getStatus());
	}

	@Test
	void testGetSynopsis() {
		Anime anime = MyAnimeList.getAnime(34096);
		assertEquals("After joining the resistance against the bakufu, Gintoki and the gang are in hiding, "
				+ "along with Katsura and his Joui rebels. The Yorozuya is soon approached by Nobume Imai "
				+ "and two members of the Kiheitai, who explain that the Harusame pirates have turned against "
				+ "7th Division Captain Kamui and their former ally Takasugi. The Kiheitai present Gintoki "
				+ "with a job: find Takasugi, who has been missing since his ship was ambushed in a Harusame raid. "
				+ "Nobume also makes a stunning revelation regarding the Tendoushuu, a secret organization "
				+ "pulling the strings of numerous factions, and their leader Utsuro, the shadowy figure with "
				+ "an uncanny resemblance to Gintoki's former teacher. Hitching a ride on Sakamoto's space ship, "
				+ "the Yorozuya and Katsura set out for Rakuyou, Kagura's home planet, where the various factions "
				+ "have gathered and tensions are brewing. Long-held grudges, political infighting, and the "
				+ "Tendoushuu's sinister overarching plan finally culminate into a massive, decisive battle "
				+ "on Rakuyou. [Written by MAL Rewrite]", anime.getSynopsis());
	}

	@Test
	void testGetTitle() {
		Anime anime = MyAnimeList.getAnime(11061);
		assertEquals("Hunter x Hunter (2011)", anime.getTitle());
	}

	@Test
	void testGetType() {
		Anime anime = MyAnimeList.getAnime(11061);
		assertEquals("TV", anime.getType());
	}

	@Test
	void testGetUrl() {
		Anime anime = MyAnimeList.getAnime(11061);
		assertEquals("https://myanimelist.net/anime/11061/Hunter_x_Hunter_2011", anime.getUrl());
	}
}
