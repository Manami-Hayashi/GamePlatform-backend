package be.kdg.prog6.chatbotContext;

import be.kdg.prog6.chatbotContext.ports.in.GetResponseUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class GetResponseUseCaseImplIntegrationTest {

    @Autowired
    private GetResponseUseCase getResponseUseCase;

    @Test
    void shouldGetResponseSuccessfully() {
        // Act
        String response = getResponseUseCase.getChatbotResponse("Hello", List.of());

        // Assert there is a response present
        assertTrue(response != null && !response.isEmpty());
    }

    @Test
    void shouldFailToGetResponse() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            getResponseUseCase.getChatbotResponse("", List.of());
        });
    }

}
