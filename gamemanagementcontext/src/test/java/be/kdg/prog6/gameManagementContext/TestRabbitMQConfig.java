//package be.kdg.prog6.gameManagementContext;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//
//@TestConfiguration
//public class TestRabbitMQConfig {
//
//@Bean
//@Primary
//public RabbitTemplate rabbitTemplate() {
//    return new RabbitTemplate() {
//        @Override
//        public void convertAndSend(String exchange, String routingKey, Object message) {
//            // Mock implementation
//        }
//    };
//}
//}