package app.service;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public class Bot implements DiscordBot, ApplicationRunner {

    private String SECRET_TOKEN = "NOPE";

    public Bot() {
        try {
            JDABuilder.createLight(SECRET_TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                    .addEventListeners(new MessageReaction())
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

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
