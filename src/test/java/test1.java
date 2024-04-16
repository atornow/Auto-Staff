import com.theokanning.openai.OpenAiService;
import com.truevanilla.plugin.OpenAiServiceHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OpenAiServiceHandlerTest {

    // Replace "your_real_openai_api_key" with your actual OpenAI API key
    private static final String API_KEY = "Your Key.";

    @Test
    void testProcessMessages() {
        OpenAiServiceHandler handler = new OpenAiServiceHandler(API_KEY);

        // Provide a message to process. Replace this with a message of your choice.
        String testMessage = "Hi my name is sweta singh and i really like to hang out and chill. I also hate the gays. I also like pizza and run in the morning. ";
        handler.processMessages(testMessage);

        // Here you can add assertions to verify the output file contents, etc.
        // However, the actual response from OpenAI's chat models can vary,
        // so it may be difficult to predict the exact output for a given input message.
    }
}
