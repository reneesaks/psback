package com.perfectstrangers.controller.open;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class UrlNotFoundController implements ErrorController {

    @GetMapping("/error")
    public ResponseEntity notFound() throws IOException {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
