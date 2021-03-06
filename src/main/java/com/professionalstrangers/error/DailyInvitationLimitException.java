package com.professionalstrangers.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "You have reached your daily invitation limit")
public class DailyInvitationLimitException extends Exception {

}
