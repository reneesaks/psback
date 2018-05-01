package com.perfectstrangers.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "You have reached your daily advert limit.")
public class DailyAdvertLimitExceeded extends Exception {

}
