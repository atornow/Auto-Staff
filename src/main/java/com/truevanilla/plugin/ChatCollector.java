package com.truevanilla.plugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import java.util.Queue;
import java.util.LinkedList;

public class ChatCollector implements Listener {
    private Queue<String> messageQueue = new LinkedList<>();
    private int totalChars = 0;

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getPlayer().getName() + ": " + event.getMessage();

        // Check if message can be added without exceeding the limit
        if (totalChars + message.length() <= 1000) {
            messageQueue.add(message);
            totalChars += message.length();
        }
    }

    public String collectAndConcatenateMessages() {
        StringBuilder builder = new StringBuilder();
        while (!messageQueue.isEmpty()) {
            builder.append(messageQueue.poll());
            builder.append(" "); // Adding a space for separation
        }
        totalChars = 0; // Reset the counter
        return builder.toString();
    }
}
