package app.service;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface DiscordBot {

    void sendMessage(MessageReceivedEvent event);

}
