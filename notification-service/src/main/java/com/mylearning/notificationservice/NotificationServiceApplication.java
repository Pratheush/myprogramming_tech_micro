package com.mylearning.notificationservice;

import com.mylearning.notificationservice.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {
/*
so whenever an order is placed an event is raised which led the Order-Service(PRODUCER) to place a notification
as message from Order-Service to the Kafka Topic and the Notification-Service is going to listen to this Kafka Topic
and its just going to print out this message so receive notification for the order number
 */
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    // Defining KafkaListener for listening to topic i.e. notificationTopic because this is the name of the topic which we gave as default in order-service (producer)
    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
        // send out an email notification
        log.info("Received Notification for Order - {}", orderPlacedEvent.getOrderNumber());
    }
}
