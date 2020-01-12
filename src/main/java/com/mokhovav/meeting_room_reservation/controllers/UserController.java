package com.mokhovav.meeting_room_reservation.controllers;

import com.mokhovav.meeting_room_reservation.datatables.Role;
import com.mokhovav.meeting_room_reservation.datatables.User;
import com.mokhovav.meeting_room_reservation.services.RoleService;
import com.mokhovav.meeting_room_reservation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/userControl")
@PreAuthorize("hasAuthority('admin')")
public class UserController {

    @Autowired
    private  UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userService.getAll());
        return "userControl";
    }
    @GetMapping("{user_id}")
    public String userEditForm(@PathVariable Long user_id, Model model) {
        model.addAttribute("user", userService.getById(user_id));
        model.addAttribute("roles",roleService.getAll());
        return "userEdit";
    }
    @PostMapping
    public String userUpdate(
            @RequestParam String userName,
            @RequestParam Map<String, String> list,
            @RequestParam Long userId,
            Model model
    ){
        User user = userService.getById(userId);
        if (!userService.isValidUserName(userName)){
            model.addAttribute("message", "User name incorrect");
            model.addAttribute("users", userService.getAll());
            return "userControl";
        }
        user.setUserName(userName);
        user.getRoles().clear();

        for (String key : list.keySet()){
            if(roleService.isExist(key)){
                user.getRoles().add(roleService.getByRoleName(key));
            }
        }
        userService.update(user);
        model.addAttribute("users", userService.getAll());
        return "userControl";
    }

    @PostMapping("addUser")
    public String addUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirm,
            Model model
    ){
        if (!userService.isValidUserName(username)){
            model.addAttribute("userMessage", "User name incorrect");
            model.addAttribute("users", userService.getAll());
            return "userControl";
        }
        if (!userService.isValidPassword(password, confirm)){
            model.addAttribute("userMessage", "Password incorrect");
            model.addAttribute("users", userService.getAll());
            return "userControl";
        }
        User user = new User(username, passwordEncoder.encode(password));

        if (userService.save(user))
            model.addAttribute("userMessage", "Success!");
        else
            model.addAttribute("userMessage", "cannot add User");
        model.addAttribute("users", userService.getAll());
        return "userControl";
    }

    @PostMapping("addRole")
    public String addRole(@RequestParam String role, Model model){
        if(roleService.save(new Role(role)))
            model.addAttribute("roleMessage","Success!");
        else
            model.addAttribute("roleMessage","cannot add role");
        model.addAttribute("users", userService.getAll());
        return "userControl";
    }
}
