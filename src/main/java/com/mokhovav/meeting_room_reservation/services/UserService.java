package com.mokhovav.meeting_room_reservation.services;

import com.mokhovav.meeting_room_reservation.datatables.Role;
import com.mokhovav.meeting_room_reservation.datatables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService { //need for WebSecurityConfig configure
    @Autowired
    private DAOService daoService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return (UserDetails) getByUserName(userName);   //need add UserDetails to User
    }

    public boolean isExist(String name) {
        return name.isEmpty() ? false : ((User)daoService.findObject("from User where username='"+name+"'")) != null;
    }

    public boolean isExist(User user) {
        return user!= null ? isExist(user.getUserName()) : false;
    }

    public boolean isExist(Long id) {
        return getById(id) != null;
    }

    public boolean isRole(User user, Role role) {
        //return (user != null && role != null) && user.getRoles().contains(role);
        if (user == null || role == null) return false;
        for (Role r : user.getRoles())
            if (r.getRoleName().equals(role.getRoleName()))
                return true;
        return false;/**/
        //return user!=null && role!= null ? user.getRoles().contains(role) : false;
    }

    public boolean save(User user){
        if(!isExist(user) && !user.getUserName().isEmpty() && !user.getPassword().isEmpty()) {
            daoService.save(user);
            return true;
        }
        return false;
    }

    public boolean update(User user) {
        if( user!=null && getById(user.getId()) != null && !user.getUserName().isEmpty() && !user.getPassword().isEmpty()) {
            daoService.update(user);
            return true;
        }
        return false;
    }

    public boolean delete(Long id) {
        User user = getById(id);
        if(user == null ) return false;
        daoService.delete(user);
        return true;
    }

    public User getByUserName(String name){
        return name.isEmpty()? null : (User)daoService.findObject("from User where username='"+name+"'");
    }

    public User getById(Long id){
        return id > 0 ? (User)daoService.findObject("from User where id="+id) : null;
    }

    public List<User> getAll(){
        return (List<User>)daoService.findAll(User.class);
    }

    public boolean isValidUserName(String userName){
        return !userName.isEmpty();
    }

    public boolean isValidPassword(String password, String confirm){
        return !password.isEmpty() && !confirm.isEmpty() && password.equals(confirm);
    }
/*
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
        for (Role r : roles)
            if(r.getRoleName().equals(role)) return false;
        //logger.info("userFromDb = "+userFromDb.getUserName()+" roleFromDb = "+roleFromDb.getRoleName()+" roles = " + roles.contains(roleFromDb));
        roles.add(roleFromDb);
        userFromDb.setRoles(roles);
        daoService.update(userFromDb);
        return true;
    }

    public boolean changePassword(User user, String password){
        if (user == null) return false;
        User userFromDb = (User) daoService.findBy(User.class, "userName", user.getUserName());
        if (userFromDb == null) return false;
        userFromDb.setPassword(passwordEncoder.encode(password));
        daoService.update(userFromDb);
        return true;
    }

    public boolean setChangePassword(User user, boolean isChange){
        if (user == null) return false;
        User userFromDb = (User) daoService.findBy(User.class, "userName", user.getUserName());
        if (userFromDb == null) return false;
        userFromDb.setChangePassword(isChange);
        daoService.update(userFromDb);
        return true;
    }

    public String checkPassword(String password, String password2) {
        if (password.isEmpty())
            return "Password is empty";
        if (!password.equals(password2))
            return "passwords do not match";
        return null;
    }

    public boolean isAccess(User user, String role) {
        boolean access = false;
        if(user == null || role==null || role.isEmpty())
            return false;
        for (Role r : user.getRoles())
            if (r.getRoleName().equals(role))
                access = true;
        return access;
    }

    public List<User> getUsers(){
        return (List<User>) daoService.findAll(User.class);
    }

    public User getUserById(Long id){
        return (User) daoService.findBy(User.class, "id", id);
    }
/**/
}
