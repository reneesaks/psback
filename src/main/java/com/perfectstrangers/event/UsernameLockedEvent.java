package com.perfectstrangers.event;

import org.springframework.context.ApplicationEvent;

public class UsernameLockedEvent extends ApplicationEvent {

    private String username;

    public UsernameLockedEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
