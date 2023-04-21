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
            case "play":
                PlaySong(event);
                break;
        }
    }

    private void PlaySong(SlashCommandInteractionEvent event) {
        OptionMapping url = event.getOption("url");
        System.out.println(url);
    }
}
