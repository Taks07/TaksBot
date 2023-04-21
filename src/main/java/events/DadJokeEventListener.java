package events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.jetbrains.annotations.NotNull;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class DadJokeEventListener extends ListenerAdapter {

    final Pattern pattern = Pattern.compile("(\s|^)i'?m (.+)", Pattern.CASE_INSENSITIVE);

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
            String dadJoke = "Hi "+ matcher.group(2) + "!";

            // Reply to message with dad joke
            event.getMessage().reply(dadJoke).mentionRepliedUser(false).queue();
        }
    }
}
