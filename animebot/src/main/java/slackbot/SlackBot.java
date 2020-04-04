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

import java.util.Map;

import myanimelist.Anime;
import myanimelist.Manga;
import myanimelist.MyAnimeList;

@JBot
@Profile("slack")
public class SlackBot extends Bot {

    private static final Logger logger = LoggerFactory.getLogger(SlackBot.class);

    /**
     * Slack token from application.properties file. You can get your slack token
     * next <a href="https://my.slack.com/services/new/bot">creating a new bot</a>.
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
     * Invoked when the bot receives a direct mention (@botname: message)
     * or a direct message.
     *
     * @param session
     * @param event
     */
    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
    public void onReceiveDM(WebSocketSession session, Event event) {
        reply(session, event, "Hi, I am " + slackService.getCurrentUser().getName());
    }


    @Controller(pattern = "get manga")
    public void onMessage(WebSocketSession session, Event event) {
        reply(session, event, "Hi, you want Manga");
    }

    @Controller(pattern = "get top manga")
    public void getTopManga(WebSocketSession session, Event event) {
        Map<Integer, Manga> topManga = MyAnimeList.getTopManga(null);
        StringBuilder msg = new StringBuilder();
        for (int rank : topManga.keySet()) {
            msg.append(rank + ": " + topManga.get(rank).getTitle() + "\r\n");
        }
        reply(session, event, "The top manga are: \r\n\r\n" + msg.toString());
    }
    
    @Controller(pattern = "get top anime")
    public void getTopAnime(WebSocketSession session, Event event) {
        Map<Integer, Anime> topAnime = MyAnimeList.getTopAnime("TV");
        StringBuilder msg = new StringBuilder();
        for (int rank : topAnime.keySet()) {
            msg.append(rank + ": " + topAnime.get(rank).getTitle() + "\r\n");
        }
        reply(session, event, "The top anime are: \r\n\r\n" + msg.toString());
    }

}