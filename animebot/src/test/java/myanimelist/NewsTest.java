package myanimelist;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NewsTest {

	@Test
	void testGetAuthorName() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals("Sakana-san", anime.getNews().get(1).getAuthorName());
	}

	@Test
	void testGetAuthorUrl() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals("https://myanimelist.net/profile/Sakana-san", anime.getNews().get(1).getAuthorUrl());
	}

	@Test
	void testGetComments() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals(7, anime.getNews().get(1).getComments());
	}

	@Test
	void testGetForumUrl() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals("https://myanimelist.net/forum/?topicid=1785218", anime.getNews().get(1).getForumUrl());
	}

	@Test
	void testGetImageUrl() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals(
				"https://cdn.myanimelist.net/s/common/uploaded_files/"
						+ "1559504276-33fe8ac74b80dc0dd28020b8685890b4.jpeg?s=c63a32e77d4b040c7178e24ded9fe756",
				anime.getNews().get(1).getImageUrl());
	}

	@Test
	void testGetIntro() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals("Here is a collection of promotional videos (PVs), TV ads (CMs), and trailers for the last week. "
				+ "This thread excludes videos that have already been featured in an art...",
				anime.getNews().get(1).getIntro());
	}

	@Test
	void testGetUrl() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals("https://myanimelist.net/news/57737361", anime.getNews().get(1).getUrl());
	}

	@Test
	void testGetTitle() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals("PV Collection for May 27 - Jun 2", anime.getNews().get(1).getTitle());
	}

}
