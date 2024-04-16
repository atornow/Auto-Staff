package com.truevanilla.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MyPlugin extends JavaPlugin {
    private static final int INTERVAL_TICKS = 20 * 60;  // Interval in server ticks (20 ticks = 1 second)
    private OpenAiServiceHandler serviceHandler;
    private ChatCollector chatCollector;

    @Override
    public void onEnable() {
        String token = "Your Token";
        serviceHandler = new OpenAiServiceHandler(token);

        // Initialize ChatCollector
        chatCollector = new ChatCollector();

        // Register the chat collector as an event listener
        getServer().getPluginManager().registerEvents(chatCollector, this);

        new BukkitRunnable() {
            @Override
            public void run() {
                String messages = collectAndConcatenateMessages();
                if (messages != null) {
                    serviceHandler.processMessages(messages);
                }
            }
        }.runTaskTimer(this, 0, INTERVAL_TICKS);
    }

    private String collectAndConcatenateMessages() {
        // We use ChatCollector's method to collect and concatenate messages
        return chatCollector.collectAndConcatenateMessages();
    }
}
