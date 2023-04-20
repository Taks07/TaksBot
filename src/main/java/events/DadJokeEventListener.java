package events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class DadJokeEventListener extends ListenerAdapter {

    static Pattern pattern = Pattern.compile("i'?m (.+)", Pattern.CASE_INSENSITIVE);


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);

        // Get contents of message and check if it matches regex pattern
        String messageContent = event.getMessage().getContentDisplay();
        Matcher matcher = pattern.matcher(messageContent);

        if (matcher.find()) {
            System.out.println("Hi "+ matcher.group(1) + "!");
        }
    }
}
