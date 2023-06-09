package events.GIFResponseEventListener;
import events.DeleteBotMessageListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GIFResponseEventListener extends ListenerAdapter {

    Map<String,String> gifMap;
    public GIFResponseEventListener() {
        gifMap = new HashMap<String,String>();

        try {
            File myFile = new File("src/main/java/events/GIFResponseEventListener/keywordtogif.txt");
            Scanner myReader = new Scanner(myFile);

            // Read through text file and add key-value pairs to map
            while (myReader.hasNextLine()) {
                String[] pair = myReader.nextLine().split(",");
                gifMap.put(pair[0], pair[1]);
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("keywordtogif.txt not found");
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        String message = event.getMessage().getContentDisplay();
        String keyword = checkForKeyword(message);

        if (keyword != null) {
            DeleteBotMessageListener.addDeleteButton(event.getMessage().reply(gifMap.get(keyword))).queue();
        }
    }

    private String checkForKeyword(String message) {
        // Loop through keys of map to see if message contains a key
        for (String keyword: gifMap.keySet()) {
            if (message.toLowerCase().contains(keyword.toLowerCase())) {
                return keyword;
            }
        }
        return null;
    }
}
