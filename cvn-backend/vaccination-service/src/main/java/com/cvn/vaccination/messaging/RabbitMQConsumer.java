package com.cvn.vaccination.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.cvn.vaccination.config.RabbitMQConstants;
import com.cvn.vaccination.event.ChildRegisteredEvent;
import com.cvn.vaccination.service.VaccineScheduleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {

    private final VaccineScheduleService vaccineScheduleService;

    @RabbitListener(queues = RabbitMQConstants.CHILD_REGISTERED_QUEUE)
    public void handleChildRegistered(ChildRegisteredEvent event) {

        log.info("Received Child Registered Event : {}", event);

        vaccineScheduleService.generateSchedule(
                event.getChildId(),
                event.getDateOfBirth()
        );
    }
}