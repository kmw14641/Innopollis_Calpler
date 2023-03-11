package com.inopolice.calpler_groovy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("")
    public String RootPage() {
        return "redirect:/select";
    }
}
