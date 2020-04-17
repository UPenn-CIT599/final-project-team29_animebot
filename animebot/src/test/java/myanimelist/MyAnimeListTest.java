package myanimelist;

import static org.junit.jupiter.api.Assertions.*;
import org.json.JSONObject;
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
	void testGetAnimeJSON() {
		JSONObject jObj = new JSONObject();
		jObj.put("mal_id", 25);
		Anime anime = MyAnimeList.getAnime(jObj);
		assertEquals("Desert Punk", anime.getEnglishTitle());
	}

	@Test
	void testGetManga() {
		int mangaId = 12;
		Manga manga = MyAnimeList.getManga(mangaId);
		assertEquals("Bleach", manga.getEnglishTitle());
	}
	@Test
	void testGetMangaJSON() {
		JSONObject jObj = new JSONObject();
		jObj.put("mal_id", 8);
		Manga manga = MyAnimeList.getManga(jObj);
		assertEquals("Full Moon wo Sagashite", manga.getEnglishTitle());
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
	void testGetUser() {
		String username = "Maxine";
		User user = MyAnimeList.getUser(username);
		assertEquals("Baltimore, Maryland", user.getLocation());
	}

	@Test
	void testMakeAPICall() {
		String query = "/manga/17/";
		String jsonResponse = MyAnimeList.makeAPICall(query);
		JSONObject jObj = new JSONObject(jsonResponse);
		String title = (String) jObj.get("title_english");
		assertEquals("Kare Kano: His and Her Circumstances", title);
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
