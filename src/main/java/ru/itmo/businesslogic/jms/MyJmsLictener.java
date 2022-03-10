package ru.itmo.businesslogic.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MyJmsLictener {

    private static final Logger logger = LoggerFactory.getLogger(MyJmsLictener.class);

    private static String message;

    @JmsListener(destination = "user.queue")
    public void handleMessage(String msg) {//implicit message type conversion
        message = msg;
        logger.info("Msg Received is.. "+msg);
    }

    public static String getMessage() {
        return message;
    }
}
