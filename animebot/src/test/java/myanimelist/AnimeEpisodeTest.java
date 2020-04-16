package myanimelist;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.TreeMap;

public class AnimeEpisodeTest {
	
	@Test
	void testGetTitle() {
		TreeMap<Integer, AnimeEpisode> episodes = MyAnimeList.getAnime(5114).getEpisodeList();
		assertEquals("Fullmetal Alchemist", episodes.get(1).getTitle());
	}
	
	@Test
	void testGetTitleRomanji() {
		TreeMap<Integer, AnimeEpisode> episodes = MyAnimeList.getAnime(5114).getEpisodeList();
		assertEquals("Hagane no Renkinjutsushi ", episodes.get(1).getTitleRomanji());
	}
	
	@Test
	void testGetVideoUrl() {
		TreeMap<Integer, AnimeEpisode> episodes = MyAnimeList.getAnime(5114).getEpisodeList();
		assertEquals("https://myanimelist.net/anime/5114/Fullmetal_Alchemist__Brotherhood/episode/1", episodes.get(1).getVideoUrl());		
	}
	
	@Test
	void testGetForumUrl() {
		TreeMap<Integer, AnimeEpisode> episodes = MyAnimeList.getAnime(5114).getEpisodeList();
		assertEquals("https://myanimelist.net/forum/?topicid=77340", episodes.get(1).getForumUrl());
	}
	
	@Test
	void testIsFiller() {
		TreeMap<Integer, AnimeEpisode> episodes = MyAnimeList.getAnime(5114).getEpisodeList();
		assertEquals(true, episodes.get(1).isFiller());
	}
	
	@Test
	void testIsRecap() {
		TreeMap<Integer, AnimeEpisode> episodes = MyAnimeList.getAnime(5114).getEpisodeList();
		assertEquals(false, episodes.get(1).isRecap());
	}
}

