package be.kdg.prog6.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.slf4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopology {
    Logger logger = org.slf4j.LoggerFactory.getLogger(RabbitMQTopology.class);

    public static final String USER_REGISTRATION_EXCHANGE = "user.registration.exchange";
    public static final String USER_REGISTRATION_QUEUE = "user.registration.queue";
    public static final String USER_REGISTRATION_ROUTING_KEY = "user.registration";

    // Define the exchange
    @Bean
    public DirectExchange userRegistrationExchange() {
        return new DirectExchange(USER_REGISTRATION_EXCHANGE);
    }

    // Define the queue
    @Bean
    public Queue userRegistrationQueue() {
        return new Queue(USER_REGISTRATION_QUEUE, true); // Durable queue
    }

    // Bind the queue to the exchange with the routing key
    @Bean
    public Binding userRegistrationBinding(Queue userRegistrationQueue, DirectExchange userRegistrationExchange) {
        return BindingBuilder
                .bind(userRegistrationQueue)
                .to(userRegistrationExchange)
                .with(USER_REGISTRATION_ROUTING_KEY);
    }

    // Admin adds a new name
    public static final String GAME_ADDED_EXCHANGE = "game.added.exchange";
    public static final String GAME_ADDED_QUEUE = "game.added.queue";
    public static final String GAME_ADDED_ROUTING_KEY = "game.added";
    @Bean
    public DirectExchange gameAddedExchange() {
        return new DirectExchange(GAME_ADDED_EXCHANGE);
    }

    @Bean
    public Queue gameAddedQueue() {
        return new Queue(GAME_ADDED_QUEUE, true); // Durable queue
    }

    @Bean
    public Binding gameAddedBinding(Queue gameAddedQueue, DirectExchange gameAddedExchange) {
        return BindingBuilder
                .bind(gameAddedQueue)
                .to(gameAddedExchange)
                .with(GAME_ADDED_ROUTING_KEY);
    }



    @Bean
    RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
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


    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }
}
