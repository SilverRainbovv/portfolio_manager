package com.didenko.rest;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HelloRestController {

    @GetMapping("/api/v1/hello")
    public String hello(){
        return "hello";
    }

}
