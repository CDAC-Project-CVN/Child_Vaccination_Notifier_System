package com.cvn.vaccination.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import com.cvn.vaccination.event.ChildRegisteredEvent;

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

        Jackson2JsonMessageConverter converter =
                new Jackson2JsonMessageConverter();

        DefaultJackson2JavaTypeMapper typeMapper =
                new DefaultJackson2JavaTypeMapper();

        typeMapper.setTrustedPackages("*");

        typeMapper.setIdClassMapping(Map.of(
                "com.cvn.user.event.ChildRegisteredEvent",
                ChildRegisteredEvent.class
        ));

        converter.setJavaTypeMapper(typeMapper);

        return converter;
    }
}
