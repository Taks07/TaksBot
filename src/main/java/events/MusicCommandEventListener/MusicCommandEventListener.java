package events.MusicCommandEventListener;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

public class MusicCommandEventListener extends ListenerAdapter {

    final AudioPlayerManager playerManager;
    final AudioPlayer player;
    AudioPlayerSendHandler audioPlayerSendHandler;
    AudioPlayerLoadHandler audioPlayerLoadHandler;

    public MusicCommandEventListener() {
        // Create AudioPlayer so bot can receive audio data
        playerManager = new DefaultAudioPlayerManager();
        playerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
        AudioSourceManagers.registerRemoteSources(playerManager);

        player = playerManager.createPlayer();
        audioPlayerSendHandler = new AudioPlayerSendHandler(player);
        audioPlayerLoadHandler = new AudioPlayerLoadHandler(player);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
        audioPlayerLoadHandler.setEvent(event);

        // Handle different commands
        switch (event.getName()) {
            case "join":
                join(event);
                break;

            case "playsong":
                playSong(event);
                break;

            case "skipsong":
                skipSong(event);
                break;

            case "clearsongs":
                clearSongs(event);
                break;

            case "leave":
                leave(event);
                break;
        }
    }

    private void join(SlashCommandInteractionEvent event) {
        // Check if sender of command is in a voice channel
        if (!event.getMember().getVoiceState().inAudioChannel()) {
            event.reply("Enter a channel first!").queue();
            return;
        }

        event.reply("Entering channel :)").queue();
        VoiceChannel currentChannel = event.getMember().getVoiceState().getChannel().asVoiceChannel();

        // Join channel and setup sending handler
        AudioManager audioManager = event.getGuild().getAudioManager();
        audioManager.openAudioConnection(currentChannel);
        audioManager.setSelfDeafened(true);
        audioManager.setSendingHandler(audioPlayerSendHandler);
    }

    private void playSong(SlashCommandInteractionEvent event) {
        // Make bot join current channel
        join(event);

        OptionMapping option = event.getOption("url");
        playerManager.loadItem(option.getAsString(), audioPlayerLoadHandler);
    }

    private void skipSong(SlashCommandInteractionEvent event) {
        event.reply("Skipping song").queue();
        audioPlayerLoadHandler.getScheduler().nextTrack();
    }

    private void clearSongs(SlashCommandInteractionEvent event) {
        event.reply("Clearing song queue").queue();
        audioPlayerLoadHandler.getScheduler().clearQueue();
    }

    private void leave(SlashCommandInteractionEvent event) {
        event.reply("Leaving channel :(").queue();
        AudioManager audioManager = event.getGuild().getAudioManager();
        audioManager.closeAudioConnection();
    }
}
