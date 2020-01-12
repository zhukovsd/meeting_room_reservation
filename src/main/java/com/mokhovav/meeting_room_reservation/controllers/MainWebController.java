package com.mokhovav.meeting_room_reservation.controllers;

import com.mokhovav.meeting_room_reservation.datatables.Role;
import com.mokhovav.meeting_room_reservation.datatables.User;
import com.mokhovav.meeting_room_reservation.services.RoleService;
import com.mokhovav.meeting_room_reservation.services.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainWebController {

    private static boolean init = false;

    final UserService userService;
    final RoleService roleService;
    final Logger logger;

    @Autowired
    PasswordEncoder passwordEncoder;

    public MainWebController(UserService userService, RoleService roleService, Logger logger) {
        this.userService = userService;
        this.roleService = roleService;
        this.logger = logger;
    }

    @GetMapping("/")
    public String index(@AuthenticationPrincipal User user, Model model){
        if (user != null && user.isChangePassword())
            return "redirect:/user/changePassword";

        if(!init) {
            roleService.save(new Role("admin"));
            userService.save(new User( "admin", passwordEncoder.encode("admin")));
            User usr = userService.getByUserName("admin");
            if(!userService.isRole(usr, roleService.getByRoleName("admin"))) {
                usr.getRoles().add(roleService.getByRoleName("admin"));
                userService.update(usr);
            }
            init = true;
        }
        return "index";
    }
}


