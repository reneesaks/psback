package com.professionalstrangers.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Only one response allowed per user")
public class ResponseLimitException extends Exception {

}
