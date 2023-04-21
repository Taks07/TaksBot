package events.MusicCommandEventListener;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class AudioPlayerLoadHandler implements AudioLoadResultHandler  {
    private final TrackScheduler scheduler;

    public AudioPlayerLoadHandler(final AudioPlayer player) {
        scheduler = new TrackScheduler(player);
        player.addListener(scheduler);
    }
    @Override
    public void trackLoaded(final AudioTrack track) {
        scheduler.getEvent().reply("Added " + scheduler.getTrackDetails(track) + " to the queue").queue();
        scheduler.queue(track);
    }

    @Override
    public void playlistLoaded(final AudioPlaylist playlist) {
        // LavaPlayer found multiple AudioTracks from some playlist
        for (AudioTrack track: playlist.getTracks()){
            scheduler.queue(track);
        }
    }

    @Override
    public void noMatches() {
        // LavaPlayer did not find any audio to extract
        scheduler.getEvent().reply("Could not find song").queue();
    }

    @Override
    public void loadFailed(final FriendlyException exception) {
        // LavaPlayer could not parse an audio source for some reason
        scheduler.getEvent().reply("LavaPlayer ran into an error. Try again").queue();
    }

    public void setEvent(SlashCommandInteractionEvent event) {
        scheduler.setEvent(event);
    }

    public TrackScheduler getScheduler() {
        return scheduler;
    }
}

