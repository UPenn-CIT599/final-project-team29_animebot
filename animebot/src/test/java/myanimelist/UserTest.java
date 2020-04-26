package myanimelist;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UserTest {
	String username = "tazillo";

	@Test
	void testGetAbout() {
		User user = MyAnimeList.getUser(username);
		assertEquals("Hi everyone!<br>\r\n" + "I'm a huge fan of anime, manga, gaming "
				+ "(mostly JRPGs and action/adventure), movies, and football. I also "
				+ "enjoy reading Japanese light novels (Spice and Wolf, Haruhi Suzumiya, "
				+ "etc), as well as fantasy fiction books. Feel free to message me if "
				+ "you'd like to ask anything :)<br><br><br>", user.getAbout());
	}

	@Test
	void testGetAnimeList() {
		User user = MyAnimeList.getUser(username);
		assertEquals("Accel World", user.getAnimeList().get(0).getTitle());
	}

	@Test
	void testGetGender() {
		User user = MyAnimeList.getUser(username);
		assertEquals("Male", user.getGender());
	}

	@Test
	void testGetImageUrl() {
		User user = MyAnimeList.getUser(username);
		assertEquals("https://cdn.myanimelist.net/images/userimages/285183.jpg?t=1587858000", user.getImageUrl());
	}

	@Test
	void testGetLocation() {
		User user = MyAnimeList.getUser(username);
		assertEquals("UK", user.getLocation());
	}

	@Test
	void testGetMangaList() {
		User user = MyAnimeList.getUser(username);
		assertEquals("Aku no Hana", user.getMangaList().get(0).getTitle());
	}

	@Test
	void testGetUrl() {
		User user = MyAnimeList.getUser(username);
		assertEquals("https://myanimelist.net/profile/tazillo", user.getUrl());
	}

	@Test
	void testGetUserName() {
		User user = MyAnimeList.getUser(username);
		assertEquals("tazillo", user.getUsername());
	}
}
