package com.perfectstrangers.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Could not connect to mail service.")
public class MailServiceNoConnectionException extends Exception {

}
