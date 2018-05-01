package com.perfectstrangers.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,
        reason = "Start and end times must be on the same date and can not be in the past.")
public class AdvertStartAndEndException extends Exception {

}
