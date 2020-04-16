package myanimelist;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NewsTest {

	@Test
	void testGetAuthorName() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals("tsubasalover", anime.getNews().get(1).getAuthorName());
	}

	@Test
	void testGetAuthorUrl() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals("https://myanimelist.net/profile/tsubasalover", anime.getNews().get(1).getAuthorUrl());
	}

	@Test
	void testGetComments() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals(13, anime.getNews().get(1).getComments());
	}

	@Test
	void testGetForumUrl() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals("https://myanimelist.net/forum/?topicid=1774615", anime.getNews().get(1).getForumUrl());
	}

	@Test
	void testGetImageUrl() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals(
				"https://cdn.myanimelist.net/s/common/uploaded_files/"
						+ "1553914878-9e5a9a1bee9fb0872a0432fcd6ca87b8.jpeg" + "?s=9453598a417ef504bc253e9457162de0",
				anime.getNews().get(1).getImageUrl());
	}

	@Test
	void testGetIntro() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals("To commemorate the 20th anniversary of the One Piece TV anime broadcast, "
				+ "the franchise's official website announced on Saturday that the Romance " + "Dawn one-shot ...",
				anime.getNews().get(1).getIntro());
	}

	@Test
	void testGetUrl() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals("https://myanimelist.net/news/57257554", anime.getNews().get(1).getUrl());
	}

	@Test
	void testGetTitle() {
		Anime anime = MyAnimeList.getAnime(21);
		assertEquals("One-Shot Manga 'Romance Dawn' Gets Another Anime Adaptation", anime.getNews().get(1).getTitle());
	}

}
