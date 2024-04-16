package com.truevanilla.plugin;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OpenAiServiceHandler {

    private static final String OUTPUT_FILE_PATH = "/Users/armaantornow1/Projects/TrueVanilla/AutoStaffPlugin/src/main/java/com/truevanilla/plugin/storage.txt";  // Replace with your desired output file path
    private final OpenAiService service;  // Making service field final since it is only set during object initialization

    public OpenAiServiceHandler(String token) {
        this.service = new OpenAiService(token);
    }

    public void processMessages(String messages) {
        // Build chat messages
        List<ChatMessage> chatMessages = new ArrayList<>();

        // Add system message
        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), "Identify any portion of the string that includes hate speech or discrimination and return each instance, and respond 'none' if none found:");
        chatMessages.add(systemMessage);

        // Add user message
        ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), messages);
        chatMessages.add(userMessage);

        // Build chat completion request
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(chatMessages)
                .n(1)
                .maxTokens(100)
                .logitBias(new HashMap<>())
                .build();

        // Send the request and process the response
        StringBuilder sb = new StringBuilder();

        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach(chatCompletionChunk -> {
                    for (ChatCompletionChoice choice : chatCompletionChunk.getChoices()) {
                        ChatMessage message = choice.getMessage();
                        if (message.getContent() != null) {
                            sb.append(message.getContent());
                        }
                    }
                });

        String result = sb.toString();
        if (!result.isEmpty()) {
            handleResponse(result);
        }
    }

    private void handleResponse(String message) {
        // Write the message to the file.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE_PATH, true))) {
            if (message != null) {
                while (message.length() > 50) {
                    String toWrite = message.substring(0, 50);
                    writer.write(toWrite);
                    writer.newLine();  // Add a newline character
                    message = message.substring(50);
                }
                writer.write(message); // write remaining part of message if less than 100 characters
                writer.newLine();  // Add a newline character to separate responses
            }
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }
}

