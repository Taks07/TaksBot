package events;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

public class MessageModifier {
    static Emoji deleteEmoji = Emoji.fromUnicode("🗑️");

    public static RestAction<Void> addDeleteButton(MessageCreateAction message) {
        return message.flatMap(botMessage -> botMessage.addReaction(deleteEmoji));
    }

}
