package events;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

import java.util.List;

public class DeleteBotMessageListener extends ListenerAdapter {
    static Emoji deleteEmoji = Emoji.fromUnicode("🗑️");

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> {
            // Check if reaction is on own message, is the deleteEmoji, and was reacted by user being replied to
            boolean isOwnMessage = message.getAuthor().equals(event.getJDA().getSelfUser());
            boolean isDeleteEmoji = event.getReaction().getEmoji().equals(deleteEmoji);
            boolean isReferencedUser;

            if (isOwnMessage & isDeleteEmoji & hasDeleteButton(message)) {
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

    private static boolean hasDeleteButton(Message message) {
        List<MessageReaction> reactions = message.getReactions();

        // Check if reaction is equal to deleteEmoji and was reacted by self (i.e. the bot)
        for (MessageReaction reaction : reactions) {
            if (reaction.getEmoji().equals(deleteEmoji) & reaction.isSelf()) {
                return true;
            }
        }

        return false;
    }
}
