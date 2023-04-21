package events.MusicCommandEventListener;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> trackQueue;
    private SlashCommandInteractionEvent event;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.trackQueue = new LinkedBlockingQueue<AudioTrack>();
    }

    public void queue(AudioTrack track) {
        // If another track is currently playing, add track to queue
        if (!player.startTrack(track, true)) {
            trackQueue.offer(track);
        } else {
            event.getChannel().sendMessage("Now playing - " + getTrackDetails(track)).queue();
        }
    }

    public void nextTrack() {
        // Starts next track no matter what
        AudioTrack currentTrack = trackQueue.poll();
        player.startTrack(currentTrack, false);
        event.getChannel().sendMessage("Now playing - " + getTrackDetails(track)).queue();
    }

    public static String getTrackDetails(AudioTrack track) {
        String name = String.valueOf(track.getInfo().title);
        String link = "https://www.youtube.com/watch?v=" + track.getIdentifier();
        return String.format("[%s] (%s)", name, link);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason reason) {
        // When current track ends, start next one
        if (reason.mayStartNext) {
            nextTrack();
        }
    }

    public void setEvent(SlashCommandInteractionEvent event) {
        this.event = event;
    }

    public SlashCommandInteractionEvent getEvent() {
        return event;
    }
}
