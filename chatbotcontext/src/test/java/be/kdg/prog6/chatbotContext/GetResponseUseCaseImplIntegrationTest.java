package be.kdg.prog6.chatbotContext;

import be.kdg.prog6.chatbotContext.ports.in.GetResponseUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=password",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=none"
})
@ActiveProfiles("test")
class GetResponseUseCaseImplIntegrationTest {

    @Autowired
    private GetResponseUseCase getResponseUseCase;

    @MockBean
    private SomeDatabaseDependency someDatabaseDependency; // Replace with actual dependency

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