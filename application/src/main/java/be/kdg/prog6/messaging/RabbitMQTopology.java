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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMQTopology {
    Logger logger = org.slf4j.LoggerFactory.getLogger(RabbitMQTopology.class);

    public static final String USER_REGISTRATION_EXCHANGE = "user.registration.exchange";
    public static final String USER_REGISTRATION_QUEUE = "user.registration.queue";
    public static final String USER_REGISTRATION_QUEUE2 = "user.registration.queue2";
    public static final String USER_REGISTRATION_QUEUE3 = "user.registration.queue3";
    public static final String USER_REGISTRATION_QUEUE4 = "user.registration.queue4";
    public static final String USER_REGISTRATION_QUEUE5 = "user.registration.queue5";

    @Bean
    public ConnectionFactory internalConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("team3_rabbitmq");
        connectionFactory.setUsername("myuser");
        connectionFactory.setPassword("mypassword");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPort(5672);
        return connectionFactory;
    }

    @Bean(name = "applicationConnectionFactory")
    @Primary // This will make it the default connection factory
    public ConnectionFactory applicationConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("myuser");
        connectionFactory.setPassword("mypassword");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPort(5673);
        return connectionFactory;
    }
    // Define the exchange
    @Bean
    public FanoutExchange userRegistrationExchange() {
        return new FanoutExchange(USER_REGISTRATION_EXCHANGE);
    }

    // Define the queue
    @Bean
    public Queue userRegistrationQueue() {
        return new Queue(USER_REGISTRATION_QUEUE, true); // Durable queue
    }

    @Bean
    public Queue userRegistrationQueue2() {
        return new Queue(USER_REGISTRATION_QUEUE2, true); // Durable queue
    }

    @Bean
    public Queue userRegistrationQueue3() {
        return new Queue(USER_REGISTRATION_QUEUE3, true); // Durable queue
    }

    @Bean
    public Queue userRegistrationQueue4() {
        return new Queue(USER_REGISTRATION_QUEUE4, true); // Durable queue
    }

    @Bean
    public Queue userRegistrationQueue5() {
        return new Queue(USER_REGISTRATION_QUEUE5, true); // Durable queue
    }

    // Bind the queue to the exchange with the routing key
    @Bean
    public Binding userRegistrationBinding(Queue userRegistrationQueue, FanoutExchange userRegistrationExchange) {
        return BindingBuilder
                .bind(userRegistrationQueue)
                .to(userRegistrationExchange);
    }

    @Bean
    public Binding userRegistrationBinding2(Queue userRegistrationQueue2, FanoutExchange userRegistrationExchange) {
        return BindingBuilder
                .bind(userRegistrationQueue2)
                .to(userRegistrationExchange);
    }

    @Bean
    public Binding userRegistrationBinding3(Queue userRegistrationQueue3, FanoutExchange userRegistrationExchange) {
        return BindingBuilder
                .bind(userRegistrationQueue3)
                .to(userRegistrationExchange);
    }

    @Bean
    public Binding userRegistrationBinding4(Queue userRegistrationQueue4, FanoutExchange userRegistrationExchange) {
        return BindingBuilder
                .bind(userRegistrationQueue4)
                .to(userRegistrationExchange);
    }

    @Bean
    public Binding userRegistrationBinding5(Queue userRegistrationQueue5, FanoutExchange userRegistrationExchange) {
        return BindingBuilder
                .bind(userRegistrationQueue5)
                .to(userRegistrationExchange);
    }

    public static final String GAME_PURCHASED_EXCHANGE = "game.purchased.exchange";
    public static final String GAME_PURCHASED_QUEUE = "game.purchased.queue";

    @Bean
    public FanoutExchange gamePurchasedExchange() {
        return new FanoutExchange(GAME_PURCHASED_EXCHANGE);
    }

    @Bean
    public Queue gamePurchasedQueue() {
        return new Queue(GAME_PURCHASED_QUEUE);
    }

    @Bean
    public Binding gamePurchasedBinding(Queue gamePurchasedQueue, FanoutExchange gamePurchasedExchange) {
        return BindingBuilder.bind(gamePurchasedQueue).to(gamePurchasedExchange);
    }


    // Admin adds a new gameName
    public static final String GAME_ADDED_EXCHANGE = "game.added.exchange";
    public static final String GAME_ADDED_QUEUE1 = "game.added.queue1";
    public static final String GAME_ADDED_QUEUE2 = "game.added.queue2";


    @Bean
    public FanoutExchange gameAddedExchange() {
        return new FanoutExchange(GAME_ADDED_EXCHANGE);
    }

    @Bean
    public Queue gameAddedQueue1() {
        return new Queue(GAME_ADDED_QUEUE1, true); // Durable queue
    }

    @Bean
    public Queue gameAddedQueue2() {
        return new Queue(GAME_ADDED_QUEUE2, true); // Durable queue
    }

    @Bean
    public Binding gameAddedBinding1(Queue gameAddedQueue1, FanoutExchange gameAddedExchange) {
        return BindingBuilder
                .bind(gameAddedQueue1)
                .to(gameAddedExchange);
    }

    @Bean Binding gameAddedBinding2(Queue gameAddedQueue2, FanoutExchange gameAddedExchange) {
        return BindingBuilder
                .bind(gameAddedQueue2)
                .to(gameAddedExchange);
    }
    

    // Define RabbitTemplate for internal communication with Keycloak (using the internalConnectionFactory)
    @Bean(name = "internalRabbitTemplate")
    public RabbitTemplate internalRabbitTemplate(@Qualifier("internalConnectionFactory") ConnectionFactory internalConnectionFactory) {
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