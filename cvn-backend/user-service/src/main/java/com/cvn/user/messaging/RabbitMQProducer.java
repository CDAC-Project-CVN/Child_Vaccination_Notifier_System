package com.cvn.user.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.cvn.user.config.RabbitMQConstants;
import com.cvn.user.event.ChildRegisteredEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publishChildRegisteredEvent(ChildRegisteredEvent event) {

        rabbitTemplate.convertAndSend(
                RabbitMQConstants.CHILD_EXCHANGE,
                RabbitMQConstants.CHILD_REGISTERED_ROUTING_KEY,
                event
        );
    }
}