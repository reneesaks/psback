package com.perfectstrangers.event.publisher;

import com.perfectstrangers.event.UsernameLockedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public class UsernameLockedEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publish(String username) {
        UsernameLockedEvent usernameLockedEvent = new UsernameLockedEvent(this, username);
        applicationEventPublisher.publishEvent(usernameLockedEvent);
    }

}
