import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMessageReactionAdd extends ListenerAdapter {

    public GuildMessageReactionAdd(){}

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event){
        if (event.getReactionEmote().getName().equals("X") &&
                !event.getMember().getUser().equals(event.getJDA().getSelfUser())) {
            System.out.println("test");
        }
    }
}
