package com.aadhie.expensetracker.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeWebController {
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
