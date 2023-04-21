package events.MusicCommandEventListener;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private BlockingQueue<AudioTrack> trackQueue;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.trackQueue = new LinkedBlockingQueue<AudioTrack>();
    }

    public void queue(AudioTrack track) {
        // If another track is currently playing, add track to queue
        if (!player.startTrack(track, true)) {
            trackQueue.offer(track);
        }
    }

    public void nextTrack() {
        // Starts next track no matter what
        player.startTrack(trackQueue.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason reason) {
        // When current track ends, start next one
        if (reason.mayStartNext) {
            nextTrack();
        }
    }
}
