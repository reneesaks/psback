package com.professionalstrangers.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "This username is already activated")
public class UsernameIsActivatedException extends Exception {

}
