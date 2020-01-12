package com.mokhovav.meeting_room_reservation.controllers;

import com.mokhovav.meeting_room_reservation.datatables.User;
import com.mokhovav.meeting_room_reservation.services.RoleService;
import com.mokhovav.meeting_room_reservation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class LoginController {
    final UserService userService;
    final RoleService roleService;

    public LoginController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("{state}")
    public String login(
            @AuthenticationPrincipal User user,
            @PathVariable String state,
            Model model
    ) {
        if (state.equals("login")) {
            model.addAttribute("state", state);
            return "login";
        }
        if (state.equals("changePassword")) {
            model.addAttribute("state", "changePassword");
            return "login";
        }
        if (state.equals("add")) {
            model.addAttribute("state", "add");
            return "login";
        }
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/changePassword")
    public String login(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "") String password ,
            @RequestParam(defaultValue = "") String confirm,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        model.addAttribute("state", "changePassword");
        if (!userService.isValidPassword(password, confirm)) {
            model.addAttribute("message", "Password incorrect");
            return "login";
        }
        user.setPassword(passwordEncoder.encode(password));
        user.setChangePassword(false);
        if (userService.update(user))
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
