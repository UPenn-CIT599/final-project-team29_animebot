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
 * The below methods follow the JBot framework guidelines such as using @Controller to
 * control what the bot looks for/says
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
				+ ". Please ask me aime related questions! Here are some commands you can use: get top manga, get top anime, anime search.");
		
    }
   
    
    /**
     * Bot looks for user saying key words or "patterns". The below method returns top manga based on MyAnimeList rating
     * @param session
     * @param event
     */
	@Controller(pattern = "([tT]op)\\s*(\\d*)\\s*(favorite)*\\s*(novels|oneshots|manga|doujin|manhwa|manhua)\\s*(bypopularity)*")
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
            }
            else if (category2 != null && !category2.isEmpty()) {
                category = category2;
            }
        }
        
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
	 * The below has the same functionality as getting top manga, but it returns top anime instead
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
            }
            else if (category2 != null && !category2.isEmpty()) {
                category = category2;
            }
            else {
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
     * Conversation starter for manga search, follows same rules above as anime search
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
    	//add code to ask user how many they want in return (1,2,3, etc)
    	 Map<Integer, Manga> mangaByTitle = MyAnimeList.searchForMangaByTitle(event.getText(), 5);
         StringBuilder msg = new StringBuilder();
         for (int rank : mangaByTitle.keySet()) {
             msg.append(rank + ": " + mangaByTitle.get(rank).getTitle() + "\r\n");
         }
         reply(session, event, "The manga are: \r\n\r\n" + msg.toString());
         stopConversation(event);
    }
       
	@Controller(pattern = "([wW]hat)\\s*(are)\\s*(the)*\\s*(episodes|shows)\\s*(for)*\\s*(\\w+)*$")
	public void getEpisodeListAnime(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(6);

		Anime animeTitle = genericTitle(anime);

		Map<Integer, AnimeEpisode> episodes = animeTitle.getEpisodeList();
		System.out.println("Number of Episodes: " + episodes.size());

		StringBuilder msgSB = new StringBuilder();

		msgSB.append("The episodes are: \r\n");
		for (int e : episodes.keySet()) {

			msgSB.append("\r\n" + e + ": " + episodes.get(e).getTitle());

			if (e == 50) {
				break;
			}
		}

		Message msg = new Message(msgSB.toString());
		msg.setMrkdwn(true);
		reply(session, event, msg);

	}
	
	
	@Controller(pattern = "([iI]s)\\s*(\\w+)\\s*(still|airing)*\\s*(airing|new|episodes|shows)\\s*(episodes)*$")
	public void animeIsAiring(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(2);
		Anime animeTitle = genericTitle(anime);
		boolean isTrueOrFalse = animeTitle.isAiring();

		if (isTrueOrFalse == true) {

			reply(session, event, anime + " is still airing!");

		} else {

			reply(session, event, anime + " is not airing!");

		}

	}
	
	
	@Controller(pattern = "([hH]ow)\\s*(many)\\s*(episodes)\\s*(does)\\s*(\\w+)\\s*(have)*$")
	public void animeNumberOfEpisodes(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(5);
		Anime animeTitle = genericTitle(anime);
		int numberOfEpisodes = animeTitle.getEpisodes();

		reply(session, event, anime + " has " + numberOfEpisodes + " episodes.");

	}
	
	//Add "what are they??" as in like what are the episodes, Once episode list is fixed...
	
	
	@Controller(pattern = "([wW]hen)\\s*(did)\\s*(\\w+\\b.*)\\s*(premiere|air)*$")
	public void animePremiered(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(3);
		Anime animeTitle = genericTitle(anime);
		String premiered = animeTitle.getPremiered();

		reply(session, event, anime + " premiered " + premiered + ".");

	}
	

	@Controller(pattern = "([wW]hat)\\s*(was)\\s*(the)\\s*(inspiration|source)\\s*(behind|for)\\s*(\\w+\\b.*)*$")
	public void animeSource(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(6);
		Anime animeTitle = genericTitle(anime);

		String source = animeTitle.getSource();

		reply(session, event, "The inspiration behind " + anime + " was:" + source);

	}
	
	
	
	@Controller(pattern = "([wW]hat|)\\s*(is)\\s*(the)\\s*(trailer)\\s*(for)\\s*(\\w+\\b.*)*$")
	public void animeTrailer(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(6);
		Anime animeTitle = genericTitle(anime);

		String trailer = animeTitle.getTrailerUrl();

		reply(session, event, "Here is the trailer URL for " + anime + ": " + trailer);

	}
	
	
	@Controller(pattern = "([tT]ell|)\\s*(me)\\s*(about)\\s*(\\w+\\b.*)*$")
	public void background(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(4);
		Anime animeTitle = genericTitle(anime);
		String backgroundOfMedia = animeTitle.getBackground();

		reply(session, event, "Here is what I have on the media " + anime + ": " + backgroundOfMedia);

	}
	
	@Controller(pattern = "([wW]hat|[gG]ive)\\s*(me|are)\\s*(the)\\s*(reviews)\\s*(for)\\s*(\\w+\\b.*)*$")
	public void reviews(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(6);
		Anime animeTitle = genericTitle(anime);
		ArrayList<Review> reviews = animeTitle.getReviews();
		
		StringBuilder msgSB = new StringBuilder();
		msgSB.append("The reviews are: \r\n\r\n");
		for (Review review : reviews) {

			msgSB.append("<" + review.getUrl() + "|" + review.getContent() + ">\r\n");

		}

		Message msg = new Message(msgSB.toString());
		msg.setMrkdwn(true);

		reply(session, event, msg);
		
	}
	
	
	
	
	
	@Controller(pattern = "([wW]hat)\\s*(is)\\s*(the)\\s*([eE]nglish)\\s*(title)\\s*(for)\\s*(\\w+\\b.*)*$")
	public void englishTitle(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(7);
		Anime animeTitle = genericTitle(anime);
		String title = animeTitle.getEnglishTitle();

		reply(session, event, "The english title for " + anime + " is " + title);

	}
	

	@Controller(pattern = "([wW]hat)\\s*(is|are)\\s*(the)\\s*(prequels|prequel)\\s*(for)\\s*(\\w+\\b.*)*$")
	public void prequel(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(6);
		Anime animeTitle = genericTitle(anime);
		
		reply(session, event, "The prequel for " + anime + " is " + animeTitle.getPrequel().get(1).getTitle());

		
	}
	
	
	@Controller(pattern = "([hH]ow|)\\s*(many)\\s*(people|users)\\s*(like)\\s*(\\w+\\b.*)*$")
	public void usersFavorite(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(5);
		Anime animeTitle = genericTitle(anime);
		int favorite = animeTitle.getFavorites();

		int members = animeTitle.getMembers();

		reply(session, event, "There are " + favorite + " users that love " + anime + ". Fun fact, " + members
				+ " members added " + anime + " to their profile!");

	}
	
	@Controller(pattern = "([aA]re|)\\s*(there)\\s*(any|news|articles)\\s*(news|articles)\\s*(for)\\s*(\\w+\\b.*)*$")
	public void news(WebSocketSession session, Event event, Matcher matcher) {

		String anime = matcher.group(6);
		Anime animeTitle = genericTitle(anime);
		ArrayList<News> articles = animeTitle.getNews();
		StringBuilder msgSB = new StringBuilder();
		msgSB.append("The news are: \r\n\r\n");
		for (News article : articles) {

			msgSB.append("<" + article.getUrl() + "|" + article.getTitle() + ">\r\n");

		}

		Message msg = new Message(msgSB.toString());
		msg.setMrkdwn(true);

		reply(session, event, msg);

	}
	
	@Controller(pattern = "([hH]ow)\\s*(many)\\s*(chapters)\\s*(does)\\s*(\\w+)\\s*(have)*$")
	public void mangaChapters(WebSocketSession session, Event event, Matcher matcher) {

		String manga = matcher.group(5);
		Manga mangaTitle = genericTitleManga(manga);
		int numberOfChapters = mangaTitle.getChapters();

		reply(session, event, manga + " has " + numberOfChapters + " chapters in the manga.");

	}
	
	
	@Controller(pattern = "([hH]ow|)\\s*(many)\\s*(people|users)\\s*(scored)\\s*(\\w+\\b.*)*$")
	public void usersScored(WebSocketSession session, Event event, Matcher matcher) {

		String manga = matcher.group(5);
		Manga mangaTitle = genericTitleManga(manga);
		int scored = mangaTitle.getScoredBy();

		

		reply(session, event, "There are " + scored + " users that scored the manga " + manga + ".");

	}
	

	@Controller(pattern = "([hH]ow)\\s*(many)\\s*(volumes)\\s*(does)\\s*(\\w+)\\s*(have)*$")
	public void mangaVolumes(WebSocketSession session, Event event, Matcher matcher) {

		String manga = matcher.group(5);
		Manga mangaTitle = genericTitleManga(manga);
		int numberOfChapters = mangaTitle.getVolumes();

		reply(session, event, manga + " has " + numberOfChapters + " volumes in the manga.");

	}
	
	
	@Controller(pattern = "([iI]s)\\s*(\\w+)\\s*(still|publishing)*\\s*(publishing|new|)\\s*(manga)*$")
	public void animeIsPulishing(WebSocketSession session, Event event, Matcher matcher) {

		String manga = matcher.group(2);
		Manga mangaTitle = genericTitleManga(manga);
		boolean isTrueOrFalse = mangaTitle.isPublishing();

		if (isTrueOrFalse == true) {

			reply(session, event, manga + " is still publishing!");

		} else {

			reply(session, event, manga + " is not publishing!");

		}

	}
	
	
	

	public Anime genericTitle(String anime) {
		
		Map<Integer, Anime> animeTitle = MyAnimeList.searchForAnimeByTitle(anime, 5);
		Anime title = animeTitle.get(1);
		return title;
		
		
	}
	
	public Manga genericTitleManga(String manga) {
		
		Map<Integer, Manga> mangaTitle = MyAnimeList.searchForMangaByTitle(manga, 5);
		Manga title = mangaTitle.get(1);
		return title;
		
		
	}
	
	
	

}