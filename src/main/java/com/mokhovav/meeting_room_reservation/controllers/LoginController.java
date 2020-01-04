package com.mokhovav.meeting_room_reservation.controllers;

import com.mokhovav.meeting_room_reservation.datatables.Role;
import com.mokhovav.meeting_room_reservation.datatables.User;
import com.mokhovav.meeting_room_reservation.services.RoleService;
import com.mokhovav.meeting_room_reservation.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    final UserService userService;
    final RoleService roleService;

    public LoginController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/changePassword")
    public String changePassword(){
        return "changePassword";
    }

    @PostMapping("checkPassword")
    public String checkPassword(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String password2,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model){

        String check = userService.checkPassword(password, password2);
        if (check != null){
            model.addAttribute("message", check);
            return "redirect:/changePassword";
        }
        if (userService.changePassword(user, password) && userService.setChangePassword(user,false))
            logoutPage(request, response);
        else
            model.addAttribute("message", "Internal Error");
        return "redirect:/";
    }

    private boolean logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
            return true;
        }
        else return false;
    }

}
