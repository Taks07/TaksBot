package events;

import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

public class MusicCommandEventListener extends ListenerAdapter{
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);

        // Handle different commands
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

        // Check if sender of command is in a voice channel
        if (!event.getMember().getVoiceState().inAudioChannel()) {
            event.reply("Enter a channel first!").mentionRepliedUser(false).queue();
            return;
        }

        VoiceChannel currentChannel = event.getMember().getVoiceState().getChannel().asVoiceChannel();

        // Join channel
        AudioManager audioManager = event.getGuild().getAudioManager();
        audioManager.openAudioConnection(currentChannel);
    }

    private void skipSong(SlashCommandInteractionEvent event) {
        System.out.println("skipping song");
    }

    private void clearSongs(SlashCommandInteractionEvent event) {
        System.out.println("clearing songs");
    }
}
