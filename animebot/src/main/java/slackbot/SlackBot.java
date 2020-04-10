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

//import example.jbot.slack.SlackBot;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.TreeMap;

import myanimelist.Anime;
import myanimelist.Manga;
import myanimelist.MyAnimeList;
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
	@Controller(pattern = "^(get top)\\s*(\\d*)\\s*(favorite)*\\s*(novels|oneshots|manga|doujin|manhwa|manhua)\\s*(bypopularity)*$")
	public void getTopManga(WebSocketSession session, Event event, Matcher matcher) {
	    // Number of results to return
        int topN = 50;
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
    @Controller(pattern = "^(get top)\\s*(\\d*)\\s*(airing|favorite|upcoming)*\\s*(anime|tv|movie|ova|special)\\s*(bypopularity)*$")
    public void getTopAnime(WebSocketSession session, Event event, Matcher matcher) {
        // Number of results to return
        int topN = 50;
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