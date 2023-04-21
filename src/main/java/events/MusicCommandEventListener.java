package events;

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

public class MusicCommandEventListener extends ListenerAdapter{

    final AudioPlayerManager playerManager;
    final AudioPlayer player;
    AudioPlayerSendHandler audioPlayerSendHandler;

    public MusicCommandEventListener() {
        // Create AudioPlayer so bot can receive audio data
        playerManager = new DefaultAudioPlayerManager();
        playerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
        AudioSourceManagers.registerRemoteSources(playerManager);
        player = playerManager.createPlayer();
        audioPlayerSendHandler = new AudioPlayerSendHandler(player);
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);

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
        }
    }

    private void join(SlashCommandInteractionEvent event) {
        // Check if sender of command is in a voice channel
        if (!event.getMember().getVoiceState().inAudioChannel()) {
            event.reply("Enter a channel first!").mentionRepliedUser(false).queue();
            return;
        }

        VoiceChannel currentChannel = event.getMember().getVoiceState().getChannel().asVoiceChannel();

        // Join channel and setup sending handler
        AudioManager audioManager = event.getGuild().getAudioManager();
        audioManager.openAudioConnection(currentChannel);
        audioManager.setSendingHandler(audioPlayerSendHandler);
    }

    private void playSong(SlashCommandInteractionEvent event) {
        OptionMapping option = event.getOption("url");


    }

    private void skipSong(SlashCommandInteractionEvent event) {
        System.out.println("skipping song");
    }

    private void clearSongs(SlashCommandInteractionEvent event) {
        System.out.println("clearing songs");
    }
}
