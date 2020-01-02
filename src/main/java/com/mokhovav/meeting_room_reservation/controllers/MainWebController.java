package com.mokhovav.meeting_room_reservation.controllers;

import com.mokhovav.meeting_room_reservation.datatables.Role;
import com.mokhovav.meeting_room_reservation.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainWebController {

    final UserService service;

    public MainWebController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index() throws Exception{
        Role role1 = new Role("USER");
        service.save(role1);
        return "index";
    }


}


