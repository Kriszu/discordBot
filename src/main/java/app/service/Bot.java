package app.service;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public class Bot implements DiscordBot{

    private String SECRET_TOKEN = "Nzc4NzQ4MjUxMTkyMjk1NDQ0.X7Wf0w.jA-4Y2vl6rMsFYP4PPz02Wk2goo";

    @Autowired
    private MessageReaction messageReaction;

    public Bot() {
        try {
            JDABuilder.createLight("Nzc4NzQ4MjUxMTkyMjk1NDQ0.X7Wf0w.jA-4Y2vl6rMsFYP4PPz02Wk2goo", GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                    .addEventListeners(messageReaction)
                    .setActivity(Activity.playing("Rough Bingo BBC"))
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(MessageReceivedEvent event) {
        try {
            if(event.getMessage().getContentRaw().equals("!test")){
                event.getChannel().sendMessage("Dziala");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
