package com.mokhovav.meeting_room_reservation.controllers;

import com.mokhovav.meeting_room_reservation.datatables.User;
import com.mokhovav.meeting_room_reservation.services.RoleService;
import com.mokhovav.meeting_room_reservation.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    final UserService userService;
    final RoleService roleService;

    public RegistrationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/registration")
    public String registration(@AuthenticationPrincipal User user, Model model){
        if (!userService.isAccess(user, "admin"))
            return "redirect:/";
        return "registration";
    }

    @PostMapping("addUser")
    public String addUser(
            @AuthenticationPrincipal User user,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role,
            Model model){

        if (!userService.isAccess(user, "admin")){
            model.addAttribute("message", "Access is denied");
            return "registration";
        }

        String check = userService.checkPassword(password, password);
        if (check != null){
            model.addAttribute("userMessage", check);
            return "registration";
        }
        if(roleService.getRole(role) == null){
            model.addAttribute("userMessage", "Role does not exist");
            return "registration";
        }
        if (userService.addUser(username, password) && userService.addRole(username, role))
            model.addAttribute("userMessage", "Success!");
        else
            model.addAttribute("message", "cannot add User");
        return "registration";
    }

    @PostMapping("addRole")
    public String addRole(@AuthenticationPrincipal User user, @RequestParam String role, Model model){
        if (!userService.isAccess(user, "admin")){
            model.addAttribute("message", "Access is denied");
            return "registration";
        }
        if(roleService.addRole(role))
            model.addAttribute("roleMessage","Success!");
        else
            model.addAttribute("roleMessage","cannot add role");
        return "registration";
    }
    @PostMapping("addUserRole")
    public String addUserRole(@AuthenticationPrincipal User user,
                              @RequestParam String userName,
                              @RequestParam String role,
                              Model model){
        if (!userService.isAccess(user, "admin")){
            model.addAttribute("message", "Access is denied");
            return "registration";
        }
        if(userService.addRole(userName, role))
            model.addAttribute("userRoleMessage","Success!");
        else
            model.addAttribute("userRoleMessage","cannot add role");
        return "registration";
    }
}
