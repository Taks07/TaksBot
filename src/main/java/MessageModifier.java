import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.requests.RestAction;

public class MessageModifier {
    Emoji deleteEmoji = Emoji.fromUnicode("🗑️");

    public static RestAction addDeleteButton(RestAction message) {
    }

}
