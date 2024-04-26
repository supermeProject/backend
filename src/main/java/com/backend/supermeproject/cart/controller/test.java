package com.backend.supermeproject.cart.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class test {

    @GetMapping("/test")
    public String test() {
        return "테스트";
    }
}
