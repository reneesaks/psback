package com.professionalstrangers.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,
        reason = "Start and end times must be on the same date, "
                + "end time must be greater than start time "
                + "and they can not be in the past")
public class InvitationTimeException extends Exception {

}
