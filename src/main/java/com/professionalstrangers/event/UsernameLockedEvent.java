package com.professionalstrangers.event;

import org.springframework.context.ApplicationEvent;

/**
 * Holds necessary info to handle username lock event.
 *
 * @see com.professionalstrangers.service.impl.UserDetailsServiceImpl
 */
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
