import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDABuilder;
public class DiscordBot {
    public static void main(String[] args) {
        // Get Discord bot token from .env file
        Dotenv dotenv = Dotenv.load();
        final String TOKEN = dotenv.get("TOKEN");

        // Create bot
        JDABuilder jdaBuilder = JDABuilder.createDefault(TOKEN);
        jdaBuilder.build();
    }

}
