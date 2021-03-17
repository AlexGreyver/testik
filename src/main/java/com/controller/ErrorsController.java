package com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorsController {

    @GetMapping("/error/403")
    public String error403() {
        return "/error/403";
    }
}
