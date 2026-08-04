package com.cvn.user.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue childRegisteredQueue() {
        return new Queue(RabbitMQConstants.CHILD_REGISTERED_QUEUE);
    }

    @Bean
    public DirectExchange childExchange() {
        return new DirectExchange(RabbitMQConstants.CHILD_EXCHANGE);
    }

    @Bean
    public Binding childBinding() {
        return BindingBuilder
                .bind(childRegisteredQueue())
                .to(childExchange())
                .with(RabbitMQConstants.CHILD_REGISTERED_ROUTING_KEY);
    }
    
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}