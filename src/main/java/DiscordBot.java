import events.DadJokeEventListener;
import events.MusicCommandEventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class DiscordBot {
    public static void main(String[] args) throws InterruptedException {
        // Get Discord bot token from .env file
        Dotenv dotenv = Dotenv.load();
        final String TOKEN = dotenv.get("TOKEN");

        // Create bot
        JDABuilder jdaBuilder = JDABuilder.createDefault(TOKEN);

        JDA jda = jdaBuilder
                  .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                  .addEventListeners(new DadJokeEventListener(), new MusicCommandEventListener())
                  .setMemberCachePolicy(MemberCachePolicy.VOICE)
                  .build().awaitReady();

        // Commands
        jda.upsertCommand("playsong", "Play a Youtube song")
           .addOption(OptionType.STRING, "url", "Youtube URL", true)
           .queue();

        jda.upsertCommand("skipsong", "Skip current song").queue();

        jda.upsertCommand("clearsongs", "Clear current playlist of songs").queue();
    }
}
