package com.omnichat.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Collections.singletonMap("message", "Hello from Spring Boot!");
    }
}