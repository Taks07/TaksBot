package events;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

public class MusicCommandEventListener extends ListenerAdapter{
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);

        switch (event.getName()) {
            case "playsong":
                playSong(event);
                break;

            case "skipsong":
                skipSong(event);
                break;

            case "clearsongs":
                clearSongs(event);
                break;
        }
    }

    private void playSong(SlashCommandInteractionEvent event) {
        OptionMapping url = event.getOption("url");
        System.out.println(url);
    }

    private void skipSong(SlashCommandInteractionEvent event) {
        System.out.println("skipping song");
    }

    private void clearSongs(SlashCommandInteractionEvent event) {
        System.out.println("clearing songs");
    }
}
