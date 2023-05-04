package events;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

public class MessageModifier extends ListenerAdapter {
    static Emoji deleteEmoji = Emoji.fromUnicode("🗑️");

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> {
            boolean isBotMessage = message.getAuthor() == event.getJDA().getSelfUser();
            boolean isNotOwnReaction = event.getUser() != event.getJDA().getSelfUser();
            boolean isDeleteEmoji = event.getReaction().getEmoji().equals(deleteEmoji);

            if (isBotMessage & isNotOwnReaction & isDeleteEmoji) {
                message.delete().queue();
            }
        });
    }
    public static RestAction<Void> addDeleteButton(MessageCreateAction message) {
        return message.flatMap(botMessage -> botMessage.addReaction(deleteEmoji));
    }

}
