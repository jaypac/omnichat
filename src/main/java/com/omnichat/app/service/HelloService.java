package com.omnichat.app.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public String getHelloWorld(String inputText) {

        return "Hello "+inputText;
    }
}
