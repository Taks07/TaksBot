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

        if (currentTrack == null) {
            return;
        }

        player.startTrack(currentTrack, false);
        event.getChannel().sendMessage("Now playing - " + getTrackDetails(currentTrack)).queue();
        outputQueue();
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

    public void outputQueue() {
        StringBuilder sb = new StringBuilder("```Current Queue:\n");
        AudioTrack track;

        // Loop through current queue, and add track names
        for (int i = 0; i < trackQueue.size(); i++) {
            track = (AudioTrack) trackQueue.toArray()[i];
            sb.append(String.format("%s. %s\n", String.valueOf(i + 1), track.getInfo().title));
        }
        sb.append("```");

        event.getChannel().sendMessage(sb.toString()).queue();
    }
    public void setEvent(SlashCommandInteractionEvent event) {
        this.event = event;
    }

    public SlashCommandInteractionEvent getEvent() {
        return event;
    }

    public void clearQueue() {
        trackQueue.clear();
        outputQueue();
    }
}
