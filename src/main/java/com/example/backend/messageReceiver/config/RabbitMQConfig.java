package com.example.backend.messageReceiver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Queue;

@Component
public class RabbitMQConfig {

    @Bean
    public Queue playerQueue() {
        return new Queue("player_queue", true);
    }


}
