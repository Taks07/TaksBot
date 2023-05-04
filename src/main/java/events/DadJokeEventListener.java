package events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class DadJokeEventListener extends ListenerAdapter {

    final Pattern pattern = Pattern.compile("(\s|^)((i'?m)|(i am))(( \\S+){1,5})(\\.|$)", Pattern.CASE_INSENSITIVE);

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);

        // Don't do anything if message is from a bot
        if (event.getAuthor().isBot()) {
            return;
        }

        // Get contents of message and check if it matches regex pattern
        String messageContent = event.getMessage().getContentDisplay();
        Matcher matcher = pattern.matcher(messageContent);

        if (matcher.find()) {
            // Check if the dad joke is name of bot
            String dadJoke = matcher.group(5);
            String botName = event.getJDA().getSelfUser().getName();

            if (dadJoke.toLowerCase().contains(botName.toLowerCase())) {
                event.getMessage().reply("That's my name! >:(").mentionRepliedUser(false).queue();
                return;
            }

            String replyMessage = "Hi" + dadJoke + "!";

            // Reply to message with dad joke
            event.getMessage().reply(replyMessage).mentionRepliedUser(false).queue();
        }
    }
}
