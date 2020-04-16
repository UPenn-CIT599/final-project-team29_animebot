package myanimelist;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MyAnimeListResultTest {

	@Test
	void testGetImageUrl() {
		Anime anime = MyAnimeList.getAnime(4181);
		assertEquals("https://cdn.myanimelist.net/images/anime/13/24647.jpg", anime.getImageUrl());
	}

	@Test
	void testGetSynopsis() {
		Anime anime = MyAnimeList.getAnime(4181);
		assertEquals("Clannad: After Story, the sequel to the critically acclaimed slice-of-life series Clannad, "
				+ "begins after Tomoya Okazaki and Nagisa Furukawa graduate from high school. Together, "
				+ "they experience the emotional rollercoaster of growing up. Unable to decide on a course for "
				+ "his future, Tomoya learns the value of a strong work ethic and discovers the strength of Nagisa's "
				+ "support. Through the couple's dedication and unity of purpose, they push forward to confront their "
				+ "personal problems, deepen their old relationships, and create new bonds. Time also moves on in "
				+ "the Illusionary World. As the plains grow cold with the approach of winter, the Illusionary Girl "
				+ "and the Garbage Doll are presented with a difficult situation that reveals the World's true purpose. "
				+ "Based on the visual novel by Key and produced by Kyoto Animation, Clannad: After Story is an impactful "
				+ "drama highlighting the importance of family and the struggles of adulthood. [Written by MAL Rewrite]",
				anime.getSynopsis());
	}

	@Test
	void testGetTitle() {
		Anime anime = MyAnimeList.getAnime(4181);
		assertEquals("Clannad: After Story", anime.getTitle());
	}

	@Test
	void testGetType() {
		Anime anime = MyAnimeList.getAnime(4181);
		assertEquals("TV", anime.getType());
	}

	@Test
	void testGetUrl() {
		Anime anime = MyAnimeList.getAnime(4181);
		assertEquals("https://myanimelist.net/anime/4181/Clannad__After_Story", anime.getUrl());
	}
}