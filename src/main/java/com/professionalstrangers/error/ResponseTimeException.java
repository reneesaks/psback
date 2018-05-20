package com.professionalstrangers.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,
        reason = "Proposed time must be between invitation's preferred start and preferred end")
public class ResponseTimeException extends Exception {

}
