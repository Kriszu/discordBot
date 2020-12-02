package app.service;

import app.service.BingoGameService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Component
public class MessageReaction extends ListenerAdapter {

    @Autowired
    private BingoGameService bingoGameService;

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        if(event.getMessage().getContentRaw().equals("!createBingo"))
            bingoGameService.createBingoGameForUser(event.getAuthor().getIdLong());
        if(event.getMessage().getContentRaw().equals("!showBingo")) event.getChannel().sendMessage(bingoGameService.showBingo()).queue();
        if(event.getMessage().getContentRaw().equals("!png")) event.getChannel().sendMessage("!pong").queue();


/*        if(event.getMessage().getContentRaw().equalsIgnoreCase("!bingo")){
            MessageChannel messageChannel = event.getChannel();
            List<String> rawMessages = new ArrayList<>();
            List<Message> messages;

            messages = messageChannel.getHistoryBefore(event.getMessageId(), 10).complete().getRetrievedHistory();

            for (Message mess: messages
                 ) {
                if(mess.getAuthor().equals(event.getAuthor())){ // dodać ! po testach
                    rawMessages.add(mess.getContentRaw());
                }
            }

            String messResponse = hasWordInBingo(rawMessages);
            if(!messResponse.equals("Na chuj mnie wołasz pajacu?")){
                messageChannel.sendMessage("Brawo! " + event.getMessage().getAuthor().getName() + "trafiłeś: " + messResponse).queue();
            } else {
                messageChannel.sendMessage(messResponse).queue();
            }
        }

        if(event.getMessage().getContentRaw().equalsIgnoreCase("!pokazbingo")){
            MessageChannel messageChannel = event.getChannel();
            messageChannel.sendMessage(bingo.bingoToString()).queue();
        }*/
    }
}
