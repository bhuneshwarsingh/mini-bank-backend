package com.bhuneshwar.mini_bank.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Bhuneshwar ✅ Token Verified!";
    }
}