package com.dsproject.joongo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @GetMapping("/api/hello")
    public String test() {
        return "Hello, world!";
    }
}
