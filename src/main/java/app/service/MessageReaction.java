package app.service;

import app.DiscordConfig;
import app.SpringContext;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.utils.tuple.Pair;
import org.h2.util.IOUtils;

import java.io.*;


public class MessageReaction extends ListenerAdapter {

    private DiscordConfig discordConfig = new DiscordConfig();

    private BingoGameService getBingoGameService(){
        return SpringContext.getBean(BingoGameService.class);
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if(event.getMessage().getContentRaw().equals("!createBingo") && event.getChannel().getName().equals(discordConfig.getBotChannelEveryone())) {
            try {
                event.getChannel().sendMessage(getBingoGameService().createBingoGameForUser(event)).queue();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(event.getMessage().getContentRaw().equals("!showBingo") &&
                (event.getChannel().getName().equals(discordConfig.getBotChannelEveryone()) ||
                event.getChannel().getName().equals(discordConfig.getBotChannelMode()))) {
            File file = new File("bingoToSend.png");
            try(OutputStream outputStream = new FileOutputStream(file)){
                FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Kriszu\\IdeaProjects\\bocik\\src\\main\\resources\\bingo.png");
                IOUtils.copy(fileInputStream, outputStream);
                event.getChannel().sendFile(file).queue();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if(event.getMessage().getContentRaw().equals("!myBingo") && event.getChannel().getName().equals(discordConfig.getBotChannelEveryone()))
            event.getChannel().sendMessageFormat("%-10s", getBingoGameService().getBingoGameForUser(event)).queue();

        if(event.getMessage().getContentRaw().equals("!bingo")){
            Pair<String, Boolean> eventResultInfo = getBingoGameService().bingoForUser(event);
            JDA jda = event.getChannel().getJDA();
            TextChannel channelToSendForUser = jda.getTextChannelsByName(discordConfig.getBotChannelEveryone(), true).get(0);
            channelToSendForUser.sendMessage(eventResultInfo.getLeft()).queue();
            if(eventResultInfo.getRight()){
                MessageChannel channelToSendForMode = jda.getTextChannelsByName(discordConfig.getBotChannelMode(),true).get(0);
                channelToSendForMode.sendMessage(getBingoGameService().bingoForMode(event)).queue();
            }
        }
        if(event.getMessage().getContentRaw().contains("!ok") && event.getChannel().getName().equals(discordConfig.getBotChannelMode())){
            event.getJDA().getTextChannelById(discordConfig.getBotChannelEveryone()).sendMessage(getBingoGameService().okCommand(event)).queue();
        }
        if(event.getMessage().getContentRaw().equals("!myBingo") && event.getChannel().getName().equals(discordConfig.getBotChannelEveryone()))
            event.getChannel().sendMessage(getBingoGameService().ranking()).queue();

        if(event.getMessage().getContentRaw().equals("!help") && event.getChannel().getName().equals(discordConfig.getBotChannelEveryone())){
            event.getChannel().sendMessage("!createBingo - tworzy Twoją planszę bingo \n" +
                    "!showBingo - pokazuje czystą planszę bingo (nie twoją), nie trzeba mieć utworzonej planszy by wyświetlić obrazek \n" +
                    "!myBingo - pokazuje twoją aktualną planszę bingo\n" +
                    "!bingo - przekazuje wiadomość do weryfikacji i wykreślenia\n" +
                    "!ranking - pokazuje ranking").queue();
        }
    }
}
