package be.kdg.prog6.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.slf4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMQTopology {
    Logger logger = org.slf4j.LoggerFactory.getLogger(RabbitMQTopology.class);

    public static final String USER_REGISTRATION_EXCHANGE = "user.registration.exchange";
    public static final String USER_REGISTRATION_QUEUE = "user.registration.queue";
    public static final String USER_REGISTRATION_ROUTING_KEY = "user.registration";

    // Custom ConnectionFactory for Keycloak internal communication (use your existing one)
    @Bean
    public ConnectionFactory internalConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("team3_rabbitmq");
        connectionFactory.setUsername("myuser");
        connectionFactory.setPassword("mypassword");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPort(5672); // This is Keycloak's port
        return connectionFactory;
    }

    // Separate ConnectionFactory for Spring Boot application (uses application.yml configuration)
    @Bean
    public ConnectionFactory applicationConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");  // Use "localhost" for Spring Boot
        connectionFactory.setUsername("myuser");
        connectionFactory.setPassword("mypassword");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPort(5673); // Use the port for Spring Boot's RabbitMQ
        return connectionFactory;
    }

    // Define the exchange
    @Bean
    public TopicExchange userRegistrationExchange() {
        return new TopicExchange(USER_REGISTRATION_EXCHANGE);
    }

    // Define the queue
    @Bean
    public Queue userRegistrationQueue() {
        return new Queue(USER_REGISTRATION_QUEUE, true); // Durable queue
    }

    // Bind the queue to the exchange with the routing key
    @Bean
    public Binding userRegistrationBinding(Queue userRegistrationQueue, TopicExchange userRegistrationExchange) {
        return BindingBuilder
                .bind(userRegistrationQueue)
                .to(userRegistrationExchange)
                .with(USER_REGISTRATION_ROUTING_KEY);
    }

    // Define RabbitTemplate for internal communication with Keycloak (using the internalConnectionFactory)
    @Bean(name = "internalRabbitTemplate")
    public RabbitTemplate internalRabbitTemplate(final ConnectionFactory internalConnectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(internalConnectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    // Define RabbitTemplate for Spring Boot application (using the applicationConnectionFactory)
    @Bean(name = "applicationRabbitTemplate")
    @Primary
    public RabbitTemplate applicationRabbitTemplate(final ConnectionFactory applicationConnectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(applicationConnectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public Jackson2JsonMessageConverter consumerJackson2MessageConverter() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    // Define a separate RabbitListenerContainerFactory for Spring Boot application
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory applicationConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(applicationConnectionFactory);
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }
}
