package com.mokhovav.meeting_room_reservation.controllers;

import com.mokhovav.meeting_room_reservation.datatables.Role;
import com.mokhovav.meeting_room_reservation.services.RoleService;
import com.mokhovav.meeting_room_reservation.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainWebController {

    final UserService userService;
    final RoleService roleService;

    public MainWebController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String index() throws Exception{
        roleService.addRole("USER");
        roleService.addRole("ADMIN");
        userService.addUser("u", "p");
        userService.addRole("u", "USER");
        return "index";
    }


}


