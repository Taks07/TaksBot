package events;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

public class DeleteBotMessageListener extends ListenerAdapter {
    static Emoji deleteEmoji = Emoji.fromUnicode("🗑️");

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> {
            // Check if reaction is on own message, is not own reaction, is the deleteEmoji, and was reacted by user being replied to
            boolean isBotMessage = message.getAuthor().equals(event.getJDA().getSelfUser());
            boolean isNotOwnReaction = !event.getUser().equals(event.getJDA().getSelfUser());
            boolean isDeleteEmoji = event.getReaction().getEmoji().equals(deleteEmoji);
            boolean isReferencedUser;

            if (isBotMessage & isNotOwnReaction & isDeleteEmoji) {
                isReferencedUser = message.getMessageReference().getMessage().getAuthor().equals(event.getUser());

                if (isReferencedUser) {
                    message.delete().queue();
                }
            }
        });
    }
    public static RestAction<Void> addDeleteButton(MessageCreateAction message) {
        return message.flatMap(botMessage -> botMessage.addReaction(deleteEmoji));
    }

}
