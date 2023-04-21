package events.MusicCommandEventListener;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class AudioPlayerLoadHandler implements AudioLoadResultHandler  {
    private final AudioPlayer player;
    private final TrackScheduler scheduler;

    public AudioPlayerLoadHandler(final AudioPlayer player) {
        this.player = player;
        scheduler = new TrackScheduler(player);
        player.addListener(scheduler);
    }
    @Override
    public void trackLoaded(final AudioTrack track) {
        scheduler.queue(track);
    }

    // Will add functionality later
    @Override
    public void playlistLoaded(final AudioPlaylist playlist) {
        // LavaPlayer found multiple AudioTracks from some playlist
    }

    @Override
    public void noMatches() {
        // LavaPlayer did not find any audio to extract

    }

    @Override
    public void loadFailed(final FriendlyException exception) {
        // LavaPlayer could not parse an audio source for some reason
    }

    public void setEvent(SlashCommandInteractionEvent event) {
        scheduler.setEvent(event);
    }

}

