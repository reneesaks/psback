package com.perfectstrangers.event;

import com.perfectstrangers.domain.User;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Profile;

/**
 * Holds necessary info to handle registration event.
 *
 * @see com.perfectstrangers.event.listener.RegistrationListener
 * @see com.perfectstrangers.controller.pub.RegistrationController
 */
@Profile({"production", "deployment"})
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private User user;

    public OnRegistrationCompleteEvent(User user) {
        super(user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}