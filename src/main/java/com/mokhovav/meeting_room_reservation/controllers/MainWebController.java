package com.mokhovav.meeting_room_reservation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainWebController {

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
