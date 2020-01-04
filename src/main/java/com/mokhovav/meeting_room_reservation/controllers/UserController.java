package com.mokhovav.meeting_room_reservation.controllers;

import com.mokhovav.meeting_room_reservation.datatables.Role;
import com.mokhovav.meeting_room_reservation.datatables.User;
import com.mokhovav.meeting_room_reservation.services.RoleService;
import com.mokhovav.meeting_room_reservation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('admin')")
public class UserController {

    @Autowired
    private  UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userService.getUsers());
        return "userList";
    }
    @GetMapping("{user_id}")
    public String userEditForm(@PathVariable Long user_id, Model model) {
        model.addAttribute("user", userService.getUserById(user_id));
        model.addAttribute("roles",roleService.getRoles());
        return "userEdit";
    }
    @PostMapping
    public String userSave(
            @RequestParam String userName,
            @RequestParam Map<String, String> list,
            @RequestParam Long userId){
        User user = userService.getUserById(userId);
        user.setUserName(userName);
        user.getRoles().clear();

        for (String key : list.keySet()){
            if(roleService.isRole(key)){
                user.getRoles().add(roleService.getRole(key));
            }
        }
        userService.update(user);
        return "redirect:/user";
    }

}
