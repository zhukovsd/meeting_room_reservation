package com.mokhovav.meeting_room_reservation.services;

import com.mokhovav.meeting_room_reservation.datatables.Role;
import com.mokhovav.meeting_room_reservation.datatables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private DAOService daoService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return (UserDetails) daoService.findBy(User.class, "userName", userName);
    }

    public boolean addUser(String userName, String password){
        User userFromDb = (User) daoService.findBy(User.class, "userName", userName);
        if (userFromDb != null) return false;
        User user = new User();
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.setActive(true);
        user.setChangePassword(true);
        daoService.save(user);
        return true;
    }

    public boolean addRole(String userName, String role){
        User userFromDb = (User) daoService.findBy(User.class, "userName", userName);
        if (userFromDb == null) return false;
        Role roleFromDb = (Role) daoService.findBy(Role.class, "roleName", role.toLowerCase());
        if (roleFromDb == null) return false;
        Set<Role> roles = userFromDb.getRoles();
        roles.add(roleFromDb);
        userFromDb.setRoles(roles);
        daoService.update(userFromDb);
        return true;
    }

}
