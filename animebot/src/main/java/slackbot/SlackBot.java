package slackbot;

import me.ramswaroop.jbot.core.common.Controller;

import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.common.JBot;
import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;

//import example.jbot.slack.SlackBot;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.TreeMap;

import myanimelist.Anime;
import myanimelist.AnimeEpisode;
import myanimelist.Manga;
import myanimelist.MyAnimeList;
import myanimelist.News;
import myanimelist.Review;
import myanimelist.enums.AnimeTopCategory;

/**
 * The below methods follow the JBot framework guidelines such as
 * using @Controller to control what the bot looks for/says
 *
 */
@JBot
@Profile("slack")
public class SlackBot extends Bot {

	private static final Logger logger = LoggerFactory.getLogger(SlackBot.class);

	/**
	 * Slack token from application.properties file
	 */
	@Value("${slackBotToken}")
	private String slackToken;

	@Override
	public String getSlackToken() {
		return slackToken;
	}

	@Override
	public Bot getSlackBot() {
		return this;
	}

	/**
	 * User can @ anime bot or direct message. The events are in an enum class
	 * defined by the JBot framework.
	 * 
	 * @param session
	 * @param event
	 */
	@Controller(events = { EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE, EventType.MEMBER_JOINED_CHANNEL })
	public void onReceiveDM(WebSocketSession session, Event event) {
		reply(session, event, "Hi, I am " + slackService.getCurrentUser().getName()
				+ ". Please ask me aime related questions, for example 'How many episodes does Inuyasha have?' You can also use some commands if you can't think of any questions: get top manga, get top anime, anime search.");

	}

	/**
	 * Bot looks for user saying key words or "patterns". The below method returns
	 * top manga based on MyAnimeList rating
	 * 
	 * @param session
	 * @param event
	 */
	@Controller(pattern = "([tT]op)\\s*(\\d*)\\s*(favorite)*\\s*(novels|oneshots|manga|doujin|manhwa|manhua)\\s*(bypopularity|by popularity)*")
	public void getTopManga(WebSocketSession session, Event event, Matcher matcher) {
		// Number of results to return
		int topN = 3;
		String numReturn = matcher.group(2);
		if (numReturn != null && !numReturn.isEmpty()) {
			topN = Integer.parseInt(numReturn);
			if (topN < 1) {
				topN = 1;
			}
		}

		// Category
		String category = matcher.group(4);
		String category1 = matcher.group(3);
		String category2 = matcher.group(5);

		if (category.equalsIgnoreCase("manga")) {
			if (category1 != null && !category1.isEmpty()) {
				category = category1;
			} else if (category2 != null && !category2.isEmpty()) {
				category = category2;
			}
		}

		category = category.replace("\\s", "");
		Map<Integer, Manga> topManga = MyAnimeList.getTopManga(category);
		StringBuilder msgSB = new StringBuilder();
		msgSB.append("The top " + topN + " " + category + " are: \r\n\r\n");

		for (int rank : topManga.keySet()) {
			Manga rankedManga = topManga.get(rank);
			msgSB.append(rank + ". <" + rankedManga.getUrl() + "|" + rankedManga.getTitle() + ">\r\n");
			if (rank == topN) {
				break;
			}
		}

		Message msg = new Message(msgSB.toString());
		msg.setMrkdwn(true);
		reply(session, event, msg);
	}

	/**
	 * The below has the same functionality as getting top manga, but it returns top
	 * anime instead
	 * 
	 * @param session
	 * @param event
	 */
	@Controller(pattern = "([tT]op)\\s*(\\d*)\\s*(airing|favorite|upcoming)*\\s*(anime|tv|movie|ova|special)\\s*(bypopularity)*")
	public void getTopAnime(WebSocketSession session, Event event, Matcher matcher) {
		// Number of results to return
		int topN = 3;
		String numReturn = matcher.group(2);
		if (numReturn != null && !numReturn.isEmpty()) {
			topN = Integer.parseInt(numReturn);
			if (topN < 1) {
				topN = 1;
			}
		}

		// Category
		String category = matcher.group(4);
		String category1 = matcher.group(3);
		String category2 = matcher.group(5);

		if (category.equalsIgnoreCase("anime")) {
			if (category1 != null && !category1.isEmpty()) {
				category = category1;
			} else if (category2 != null && !category2.isEmpty()) {
				category = category2;
			} else {
				category = "bypopularity";
			}
		}

		Map<Integer, Anime> topAnime = MyAnimeList.getTopAnime(category);
		StringBuilder msgSB = new StringBuilder();
		msgSB.append("The top " + topN + " " + category + " are: \r\n\r\n");
		for (int rank : topAnime.keySet()) {
			Anime rankedAnime = topAnime.get(rank);
			msgSB.append(rank + ". <" + rankedAnime.getUrl() + "|" + rankedAnime.getTitle() + ">\r\n");
			if (rank == topN) {
				break;
			}
		}

		Message msg = new Message(msgSB.toString());
		msg.setMrkdwn(true);
		reply(session, event, msg);
	}

	/**
	 * The below method is the start of a conversation with a bot, the pattern
	 * search looks for the user asking to "anime search" aka, search the DB of
	 * anime, and then moves on to the next method. The methods know to interact
	 * with each other due to the "next" field containing the name of the next
	 * method that follows. For example next = "whatAnime" search for the method
	 * named "whatAnime" to continue the conversation
	 * 
	 * @param session
	 * @param event
	 */
	@Controller(pattern = "[aA]nime search", next = "whatAnime")
	public void searchForAnimeByTitle(WebSocketSession session, Event event) {
		startConversation(event, "whatAnime");
		reply(session, event, "What anime do you want to search by title?");

	}

	@Controller
	public void whatAnime(WebSocketSession session, Event event) {

		Map<Integer, Anime> animeByTitle = MyAnimeList.searchForAnimeByTitle(event.getText(), 5);
		StringBuilder msg = new StringBuilder();
		for (int rank : animeByTitle.keySet()) {
			msg.append(rank + ": " + animeByTitle.get(rank).getTitle() + "\r\n");
		}
		reply(session, event, "The anime are: \r\n\r\n" + msg.toString());
		stopConversation(event);
	}

	/**
	 * Conversation starter for manga search, follows same rules above as anime
	 * search
	 * 
	 * @param session
	 * @param event
	 */
	@Controller(pattern = "[mM]anga search", next = "whatManga")
	public void searchForMangaByTitle(WebSocketSession session, Event event) {
		startConversation(event, "whatManga");
		reply(session, event, "What manga do you want to search by title?");

	}

	@Controller
	public void whatManga(WebSocketSession session, Event event) {
		// add code to ask user how many they want in return (1,2,3, etc)
		Map<Integer, Manga> mangaByTitle = MyAnimeList.searchForMangaByTitle(event.getText(), 5);
		StringBuilder msg = new StringBuilder();
		for (int rank : mangaByTitle.keySet()) {
			msg.append(rank + ": " + mangaByTitle.get(rank).getTitle() + "\r\n");
		}
		reply(session, event, "The manga are: \r\n\r\n" + msg.toString());
		stopConversation(event);
	}

	/**
	 * The below gets the episodes for the anime entered. The method also checks if
	 * there is a filler or a recap for the specific episode. This is set to 50
	 * episodes to not overload.
	 * 
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([wW]hat)\\s*(are)\\s*(the)*\\s*(episodes|shows)\\s*(for)*\\s*(\\w+\\b.*)*")
	public void getEpisodeListAnime(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(6);

		Anime animeTitle = genericTitle(anime);

		Map<Integer, AnimeEpisode> episodes = animeTitle.getEpisodeList();
		StringBuilder msgSB = new StringBuilder();

		msgSB.append("The episodes are:");
		for (int e : episodes.keySet()) {

			msgSB.append("\r\n\r\n" + e + ": " + episodes.get(e).getTitle());
			msgSB.append("\r\n The Romanji title is: " + episodes.get(e).getTitleRomanji());

			if (episodes.get(e).isFiller() == true) {
				msgSB.append("\r\n Fun fact! This episode is a filler!\r\n");

			}
			if (episodes.get(e).isRecap() == true) {
				msgSB.append("\r\n Fun fact! This episode is a recap!\r\n");

			}

			if (e == 50) {
				break;
			}
		}

		Message msg = new Message(msgSB.toString());
		msg.setMrkdwn(true);
		reply(session, event, msg);

	}

	/**
	 * The below takes in a command relating to airing new episodes, which animebot answers with yes or no if it is or isn't.
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([iI]s)\\s*(\\w+)\\s*(still|airing)*\\s*(airing|episodes|shows)\\s*(episodes)*")
	public void animeIsAiring(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(2);
		Anime animeTitle = genericTitle(anime);
		boolean isTrueOrFalse = animeTitle.isAiring();

		if (isTrueOrFalse == true) {

			reply(session, event, genericTitle(anime).getTitle() + " is still airing!");

		} else {

			reply(session, event, genericTitle(anime).getTitle() + " is not airing!");

		}

	}

	/**
	 * Animebot checks what the status for the media (Anime/Manga) is (airing, paused, etc)
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([wW]hat)\\s*(is)\\s*(the)*\\s*(status)\\s*(of|for)\\s*(\\w+\\b.*)*")
	public void statusOfMedia(WebSocketSession session, Event event, Matcher matcher) {

		String name = matcher.group(6);
		Anime animeTitle = genericTitle(name);
		Manga mangaTitle = genericTitleManga(name);
		String status = animeTitle.getStatus();

		reply(session, event, "Here is the status for the anime: " + animeTitle.getStatus()
				+ "\r\n Here is the status for the manga: " + mangaTitle.getStatus());

	}

	/**
	 * The below method checks how many episodes are in the given anime.
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([hH]ow)\\s*(many)\\s*(episodes)\\s*(does)\\s*(\\w+)\\s*(have)*$")
	public void animeNumberOfEpisodes(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(5);
		Anime animeTitle = genericTitle(anime);
		int numberOfEpisodes = animeTitle.getEpisodes();

		reply(session, event, genericTitle(anime).getTitle() + " has " + numberOfEpisodes + " episodes.");

	}
	/**
	 * the below checks when the anime premiered.
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([wW]hen)\\s*(did)\\s*(\\w+)\\s*(premiere|air)*")
	public void animePremiered(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(3);
		Anime animeTitle = genericTitle(anime);
		String premiered = animeTitle.getPremiered();

		reply(session, event, genericTitle(anime).getTitle() + " premiered " + premiered + ".");

	}

	/**
	 * Checks the inspiration for the anime (whether it was manga, original, etc)
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([wW]hat)\\s*(was)\\s*(the)\\s*(inspiration|source)\\s*(behind|for)\\s*(\\w+\\b.*)*")
	public void animeSource(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(6);
		Anime animeTitle = genericTitle(anime);

		String source = animeTitle.getSource();

		reply(session, event, "The inspiration behind " + genericTitle(anime).getTitle() + " was:" + source);

	}

	/**
	 * Gives the trailer for the anime.
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([wW]hat|)\\s*(is)\\s*(the)\\s*(trailer)\\s*(for)\\s*(\\w+\\b.*)*")
	public void animeTrailer(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(6);
		Anime animeTitle = genericTitle(anime);
		

		String trailer = animeTitle.getTrailerUrl();

		reply(session, event, "Here is the trailer URL for " + genericTitle(anime).getTitle() + ": " + trailer);

	}

	/**
	 * Gives more details about the media.
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([tT]ell|[gG]ive)\\s*(me)\\s*(more|about)*\\s*(details)\\s*(about)*\\s*(\\w+\\b.*)*$")
	public void background(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(6);
		Anime animeTitle = genericTitle(anime);
		String backgroundOfMedia = animeTitle.getBackground();

		reply(session, event, "Here is what I have on the media " + anime + ": \r\n " + backgroundOfMedia);

	}

	/**
	 * Returns the reviews for the anime in question. Only 1 review will show due to overloading the API.
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([wW]hat|[gG]ive)\\s*(me|are)*\\s*(the)*\\s*(reviews)\\s*(for)*\\s*(\\w+\\b.*)*")
	public void reviews(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(6);
		Anime animeTitle = genericTitle(anime);
		ArrayList<Review> reviews = animeTitle.getReviews();

		StringBuilder msgSB = new StringBuilder();
		msgSB.append("Here is the review: \r\n\r\n");
		int count = 0;
		for (Review review : reviews) {
			count++;

			msgSB.append("<" + review.getUrl() + "|" + "Link to the review." + ">" + "\r\n"+ review.getContent() +"\r\n\r\n");
			msgSB.append(review.getHelpfulCount()+ " users found this review helpful.");

			if (count >= 1) {

				break;
			}

		}

		Message msg = new Message(msgSB.toString());
		msg.setMrkdwn(true);

		reply(session, event, msg);

	}
	
	
	/**
	 * Returns English title if you enter a Japanese title.
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([wW]hat)\\s*(is)\\s*(the)\\s*([eE]nglish)\\s*(name|title)\\s*(for)\\s*(\\w+\\b.*)*")
	public void englishTitle(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(7);
		Anime animeTitle = genericTitle(anime);
		String title = animeTitle.getEnglishTitle();

		reply(session, event, "The english title for " + genericTitle(anime).getTitle() + " is " + title);

	}

	/**
	 * Returns the prequel for the anime and manga.
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([wW]hat)\\s*(is|are)\\s*(the)\\s*(prequels|prequel)\\s*(for)\\s*(\\w+\\b.*)*")
	public void animePrequel(WebSocketSession session, Event event, Matcher matcher) {

		String title = matcher.group(6);
		Anime animeTitle = genericTitle(title);
		
		if(animeTitle.getPrequel().size() == 0) {
			
			reply(session, event, "There are no prequels!");
				
			
		}
	
		else {
		reply(session, event, "The prequel is: " + animeTitle.getPrequel().get(0).getTitle());
		}
		

	}
	/**
	 * Returns prequel for the anime in question
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([wW]hat)\\s*(is|are)\\s*(the)\\s*(sequels|sequel)\\s*(for)\\s*(\\w+\\b.*)*")
	public void animeSequel(WebSocketSession session, Event event, Matcher matcher) {

		String title = matcher.group(6);
		Anime animeTitle = genericTitle(title);

		ArrayList<Anime> sequels = animeTitle.getSequel();
		
		StringBuilder msgSB = new StringBuilder();
		int count = 1;
		msgSB.append("Here are the sequels: \r\n\r\n");
		for (Anime sequel: sequels) {

			msgSB.append(count +". " +sequel.getTitle());
			
			if (count >= 10) {

				break;
			}

			

		}

		Message msg = new Message(msgSB.toString());
		msg.setMrkdwn(true);

		reply(session, event, msg);
	}




	/**
	 * Returns photo URLs for specified anime/manga
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([sS]how)*\\s*(me)*\\s*(pictures|photos)\\s*(of|for)\\s*(\\w+\\b.*)*")
	public void pictures(WebSocketSession session, Event event, Matcher matcher) {
		String title = matcher.group(5);
		Manga mangaTitle = genericTitleManga(title);
		Anime animeTitle = genericTitle(title);

		StringBuilder msgSB = new StringBuilder();

		msgSB.append("Here are picture URL's for the anime: \r\n");
		for (String e : animeTitle.getPictures()) {

			msgSB.append("\r\n" + e);

		}

		msgSB.append("\r\n Here are picture URL's for the manga: \r\n");
		for (String e : mangaTitle.getPictures()) {

			msgSB.append("\r\n" + e);

		}

		Message msg = new Message(msgSB.toString());
		msg.setMrkdwn(true);
		reply(session, event, msg);
	}
	
	/**
	 * Returns recommendations for anime/manga in question
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([gG]ive)*\\s*(me)*\\s*(recommendations|recs)\\s*(of|for)\\s*(\\w+\\b.*)*")
	public void recommendations(WebSocketSession session, Event event, Matcher matcher) {
		String title = matcher.group(5);
		Manga mangaTitle = genericTitleManga(title);
		Anime animeTitle = genericTitle(title);

		Map<Integer, Anime> animeRecommendations = animeTitle.getRecommendations();
		Map<Integer, Manga> mangaRecommendations = mangaTitle.getRecommendations();

		StringBuilder msgSB = new StringBuilder();

		msgSB.append("Here are the recommendations for the anime: \r\n");
		for (int e : animeRecommendations.keySet()) {

			msgSB.append("\r\n" + e + ". " + animeRecommendations.get(e).getTitle());

			if (e == 20) {
				break;
			}

		}

		msgSB.append("\r\n\r\n Here are the recommendations for the manga: \r\n");
		for (int e : mangaRecommendations.keySet()) {

			msgSB.append("\r\n" + e + ". " + mangaRecommendations.get(e).getTitle());

			if (e == 20) {
				break;
			}

		}

		Message msg = new Message(msgSB.toString());
		msg.setMrkdwn(true);
		reply(session, event, msg);
	}

	
	/**
	 * Returns how many users like the anime in question
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([hH]ow|)\\s*(many)\\s*(people|users)\\s*(like|liked|enjoyed|loved)\\s*(\\w+\\b.*)*")
	public void usersFavorite(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(5);
		Anime animeTitle = genericTitle(anime);
		int favorite = animeTitle.getFavorites();

		int members = animeTitle.getMembers();

		reply(session, event, "There are " + favorite + " users that love " + genericTitle(anime).getTitle() + ". \r\nFun fact, " + members
				+ " members added " + genericTitle(anime).getTitle() + " to their profile!");

	}
	
	/**
	 * Returns overall rank of anime
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([hH]ow|)\\s*(popular)\\s*(is)\\s*(\\w+\\b.*)*$")
	public void popularity(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(4);
		Anime animeTitle = genericTitle(anime);
		int ranking = animeTitle.getPopularity();

		reply(session, event, genericTitle(anime).getTitle() + "'s overall rank is " + ranking + ".");

	}
	
	/**
	 * Returns news articles 
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([aA]re)*\\s*(there)*\\s*(any|news|articles)\\s*(news|articles)\\s*(articles|for)*\\s(for)*\\s*(\\w+\\b.*)*")
	public void news(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(7);
		Anime animeTitle = genericTitle(anime);
		ArrayList<News> articles = animeTitle.getNews();
		StringBuilder msgSB = new StringBuilder();
		msgSB.append("The news are: \r\n\r\n");
		int counter = 0;
		for (News article : articles) {
			counter++;
			msgSB.append("Article " + counter + ": " + "<" + article.getUrl() + "|" + article.getTitle() + ">\r\n");
			msgSB.append("Author: " + "<" + article.getAuthorUrl() + "|" + article.getAuthorName() + ">\r\n");
			msgSB.append("Here is the intro to the article in case you are interested: \r\n" + article.getIntro()
					+ "\r\n\r\n");

			if (counter == 10) {
				break;
			}
		}

		Message msg = new Message(msgSB.toString());
		msg.setMrkdwn(true);

		reply(session, event, msg);

	}
	
	/**
	 * Returns number of chapters for the manga version
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([hH]ow)\\s*(many)\\s*(chapters)\\s*(does)\\s*(\\w+)\\s*(have)*")
	public void mangaChapters(WebSocketSession session, Event event, Matcher matcher) {

		String manga = matcher.group(5);
		Manga mangaTitle = genericTitleManga(manga);
		int numberOfChapters = mangaTitle.getChapters();

		reply(session, event, genericTitle(manga).getTitle() + " has " + numberOfChapters + " chapters in the manga.");

	}
	
	/**
	 * Returns how many users scored the manga
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([hH]ow|)\\s*(many)\\s*(people|users)\\s*(scored)\\s*(\\w+\\b.*)*")
	public void usersScored(WebSocketSession session, Event event, Matcher matcher) {

		String manga = matcher.group(5);
		Manga mangaTitle = genericTitleManga(manga);
		int scored = mangaTitle.getScoredBy();

		reply(session, event, "There are " + scored + " users that scored the manga " + genericTitle(manga).getTitle() + ".");

	}

	/**
	 * Returns the score for both media
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([wW]hat|)\\s*(is)\\s*(the)\\s*(score)\\s*(for)\\s*(\\w+\\b.*)*")
	public void mediaScore(WebSocketSession session, Event event, Matcher matcher) {

		String title = matcher.group(6);
		Manga mangaTitle = genericTitleManga(title);
		Anime animeTitle = genericTitle(title);

		reply(session, event, "The average user score for the anime is " + animeTitle.getScore()
				+ ".\r\n The average user score for the manga is " + mangaTitle.getScore() + ".");

	}
	
	/**
	 * Returns sumamry for the anime and manga
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([wW]hat)\\s*(is)\\s*(the)\\s*(synopsis|summary)\\s*(for|of)\\s*(\\w+\\b.*)*")
	public void mediaSynopsis(WebSocketSession session, Event event, Matcher matcher) {

		String title = matcher.group(6);
		Manga mangaTitle = genericTitleManga(title);
		Anime animeTitle = genericTitle(title);

		reply(session, event, "Here is the synopsis for the anime: " + animeTitle.getSynopsis()
				+ ".\r\n\r\n Here is the synopsis for the manga: " + mangaTitle.getSynopsis() + ".");

	}
	/**
	 * Returns the number of volumes in the manga
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([hH]ow)\\s*(many)\\s*(volumes)\\s*(does)\\s*(\\w+)\\s*(have)*$")
	public void mangaVolumes(WebSocketSession session, Event event, Matcher matcher) {

		String manga = matcher.group(5);
		Manga mangaTitle = genericTitleManga(manga);
		int numberOfVolumes = mangaTitle.getVolumes();

		reply(session, event, genericTitle(manga).getTitle() + " has " + numberOfVolumes + " volumes in the manga.");

	}
	
	
	/**
	 * Returns whether or not the manga is still being published
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([iI]s)\\s*(\\w+)\\s*(still|publishing)\\s*(publishing|new)\\s*(new)*\\s*(manga)*")
	public void animeIsPublishing(WebSocketSession session, Event event, Matcher matcher) {

		String manga = matcher.group(2);
		Manga mangaTitle = genericTitleManga(manga);
		boolean isTrueOrFalse = mangaTitle.isPublishing();

		if (isTrueOrFalse == true) {

			reply(session, event, genericTitle(manga).getTitle() + " is still publishing!");

		} else {

			reply(session, event, genericTitle(manga).getTitle() + " is not publishing!");

		}

	}

	/**
	 * Does a comparison between the anime and manga
	 * @param session
	 * @param event
	 * @param matcher
	 */
	@Controller(pattern = "([wW]as)\\s*(the)\\s*(manga|anime)\\s*(or|better)*\\s(the)*\\s*(anime|manga|than)*\\s*(better|the)*\\s*(anime|manga)*\\s*(for|of)\\s*(\\w+\\b.*)*")
	public void compareTo(WebSocketSession session, Event event, Matcher matcher) {

		String title = matcher.group(9);
		Manga mangaTitle = genericTitleManga(title);
		Anime animeTitle = genericTitle(title);

		if (animeTitle.compareTo(mangaTitle) == -1) {

			reply(session, event, "The manga is more popular compared to the anime.");

		} else {

			reply(session, event, "The anime is more popular compared to the manga.");

		}

	}

	/**
	 * Created a generic title for the anime to avoid repeating code.
	 * @param anime
	 * @return
	 */
	public Anime genericTitle(String anime) {

		Map<Integer, Anime> animeTitle = MyAnimeList.searchForAnimeByTitle(anime, 5);
		Anime title = animeTitle.get(1);
		return title;

	}

	/**
	 * Created generic title for the manga to avoid repeating code.
	 * @param manga
	 * @return
	 */
	public Manga genericTitleManga(String manga) {

		Map<Integer, Manga> mangaTitle = MyAnimeList.searchForMangaByTitle(manga, 5);
		Manga title = mangaTitle.get(1);
		return title;

	}

}