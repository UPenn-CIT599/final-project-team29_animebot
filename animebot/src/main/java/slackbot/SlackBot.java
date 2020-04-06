package slackbot;
import me.ramswaroop.jbot.core.common.Controller;

import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.common.JBot;
import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.models.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.socket.WebSocketSession;

//import example.jbot.slack.SlackBot;

import java.util.Map;
import java.util.TreeMap;

import myanimelist.Anime;
import myanimelist.Manga;
import myanimelist.MyAnimeList;

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
	@Controller(events = { EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE })
	public void onReceiveDM(WebSocketSession session, Event event) {
    	reply(session, event, "Hi, I am " + slackService.getCurrentUser().getName()
				+ ". Please ask me aime related questions! Here are some commands you can use: get top manga, get top anime, anime search.");
		
    }
    
    /**
	@Controller(events = { EventType.USER_TYPING })
	public void userTypes(WebSocketSession session, Event event) {
		reply(session, event, "Hi, I am " + slackService.getCurrentUser().getName()
				+ ". Please ask me aime related questions! Here are some commands you can use: get top manga, get top anime, anime search.");
		stopConversation(event);
	}
	**/
    
    /**
     * Bot looks for user saying key words or "patterns". The below method returns top manga based on MyAnimeList rating
     * @param session
     * @param event
     */
	@Controller(pattern = "get top manga")
	public void getTopManga(WebSocketSession session, Event event) {
		Map<Integer, Manga> topManga = MyAnimeList.getTopManga(null);
		StringBuilder msg = new StringBuilder();
		for (int rank : topManga.keySet()) {
            msg.append(rank + ": " + topManga.get(rank).getTitle() + "\r\n");
        }
        reply(session, event, "The top manga are: \r\n\r\n" + msg.toString());
    }
    
	
	/**
	 * The below has the same functionality as getting top manga, but it returns top anime instead
	 * @param session
	 * @param event
	 */
    @Controller(pattern = "get top anime")
    public void getTopAnime(WebSocketSession session, Event event) {
        Map<Integer, Anime> topAnime = MyAnimeList.getTopAnime("TV");
        StringBuilder msg = new StringBuilder();
        for (int rank : topAnime.keySet()) {
            msg.append(rank + ": " + topAnime.get(rank).getTitle() + "\r\n");
        }
        reply(session, event, "The top anime are: \r\n\r\n" + msg.toString());
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
    @Controller(pattern = "anime search", next = "whatAnime")
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
    @Controller(pattern = "manga search", next = "whatManga")
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
       
     

}