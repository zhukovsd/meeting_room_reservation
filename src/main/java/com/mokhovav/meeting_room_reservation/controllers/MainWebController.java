package com.mokhovav.meeting_room_reservation.controllers;

import com.mokhovav.meeting_room_reservation.datatables.Role;
import com.mokhovav.meeting_room_reservation.datatables.User;
import com.mokhovav.meeting_room_reservation.services.RoleService;
import com.mokhovav.meeting_room_reservation.services.UserService;
import org.slf4j.Logger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainWebController {

    private static boolean init = false;

    final UserService userService;
    final RoleService roleService;
    final Logger logger;

    public MainWebController(UserService userService, RoleService roleService, Logger logger) {
        this.userService = userService;
        this.roleService = roleService;
        this.logger = logger;
    }

    @GetMapping("/")
    public String index(@AuthenticationPrincipal User user, Model model){
        //TODO:
        if (user != null && user.isChangePassword())
            return "redirect:changePassword";
        if(!init) {
            roleService.addRole("admin");
            userService.addUser("admin", "admin");
            userService.addRole("admin", "admin");
            init = true;

            User usr = userService.getUserById((long)1);
            logger.info("admin = " + usr.isRole(roleService.getRole("admin")));
            logger.info("user = " + usr.isRole(roleService.getRole("user")));
            logger.info("developer = " + usr.isRole(roleService.getRole("developer")));
        }
        model.addAttribute("adminAccess", userService.isAccess(user, "admin"));
        if (user != null)
            model.addAttribute("isAccess", true);
        return "index";
    }

    @GetMapping("/conferences")
    public String conferences(@AuthenticationPrincipal User user, Model model){
        //TODO:
        if (user != null && user.isChangePassword())
            return "redirect:changePassword";
        model.addAttribute("username",user.getUserName());
        return "conferences";
    }


}


