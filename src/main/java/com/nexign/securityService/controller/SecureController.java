package com.nexign.securityService.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecureController {

    @GetMapping
    public String getHello() {
        return "hello.html";
    }
}
