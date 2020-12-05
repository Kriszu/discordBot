package app;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Map;

public class DiscordConfig {

    private String botChannelMode = "botchannelhidden";
    private String botChannelEveryone = "botchanneleveryone";
    private Map<String, String> messages = Map.of(
            "BingoAlreadyCreated", "Debilu jebany masz już utworzone",
            "BingoCreated", "Bingo utworzone siadaj mi na chuja",
            "UnexpectedFailure", "No i chuj kriszu coś zjebał",
            "BingoDoesntExists", "Nie utworzyłeś bingo glupi chuju",
            "BingoAlreadyExsists", "Bingo istnieje!",
            "Winner", "UWAGA TA JEBANA KURWA SZMATA DZIWKA ZAJEBANA MA BINGO!!!",
            "BingoNotificationForUser", "Dziweczko zgłosiłeś bingo do zweryfikowania",
            "BingoNotificationForMode", "zgłosił bingo: ",
            "BingoBoard","Twoje Bingo wyżej głupi chuju",
            "BingoWordDoesntExsists", "Kriszu pojebał komendy"
            );
    private Map<String, String> messages2 = Map.of(
            "AlreadyHasScore", "Masz już to skreślony pierdolony debilu",
            "ImageMergeFailure", "Nie udało się zakutalizować Twojego bingo, wołaj tego śmiecia programistę za 5 groszy",
            "mgResetFailure", "Nie udało się zresetować obrazów, wołaj tego śmiecia programistę za 5 groszy",
            "WinnerAndReset", "to wygryw, wlatuje resecik i punkty na suke doliczone",
            "BingoAccepted", "twoje zgłoszenie zostało zaakceptowane",
            "UnhandledFailure", "coś się wyjebało"
            );
    private String roleMode = "mode";


    public String getMessage(String s, MessageReceivedEvent event){
        String results = "<@"+event.getAuthor().getId()+"> ";
        if(messages.containsKey(s)){
            results += messages.get(s);
        }
        if(messages2.containsKey(s)){
            results += messages2.get(s);

        }
        if(s.equals("BingoNotificationForUser") || s.equals("BingoNotificationForMode")){
            results += " " + event.getMessage().getJumpUrl();
        }
        return results;
    }
    public String getBotChannelMode() {
        return botChannelMode;
    }

    public String getBotChannelEveryone() {
        return botChannelEveryone;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public String getRoleMode() {
        return roleMode;
    }
}
