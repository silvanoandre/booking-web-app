package com.sapm.booking.app.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class MvcHomeController {

    @GetMapping("/")
    public String home() {
        return "views/home";
    }


}

