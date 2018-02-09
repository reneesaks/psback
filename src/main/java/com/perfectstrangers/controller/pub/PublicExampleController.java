package com.perfectstrangers.controller.pub;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/public")
public class PublicExampleController {

    @GetMapping(value = "/hello")
    public String sayHello() {
        return "Hello!";
    }
}
