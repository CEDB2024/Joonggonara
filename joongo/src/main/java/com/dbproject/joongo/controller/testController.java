package com.dbproject.joongo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @GetMapping("/api/hello")
    public String test() {
        return "Hello, world!";
    }
}
