import events.DadJokeEventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordBot {
    public static void main(String[] args) {
        // Get Discord bot token from .env file
        Dotenv dotenv = Dotenv.load();
        final String TOKEN = dotenv.get("TOKEN");

        // Create bot
        JDABuilder jdaBuilder = JDABuilder.createDefault(TOKEN);

        jdaBuilder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                  .build()
                  .addEventListener(new DadJokeEventListener());
    }
}
