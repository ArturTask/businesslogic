package ru.itmo.businesslogic.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;



@Component
public class MyJmsListener implements RabbitListenerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(MyJmsListener.class);

    private static String message;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
    }
    @RabbitListener(queues = "user.queue")
    public void receivedMessage(String msg) {
        message = msg;
        logger.info("Msg Received is.. " + msg);
    }

    public static String getMessage() {
        return message;
    }
}