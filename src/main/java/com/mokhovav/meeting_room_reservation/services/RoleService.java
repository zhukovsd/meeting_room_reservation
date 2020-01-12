package com.mokhovav.meeting_room_reservation.services;

import com.mokhovav.meeting_room_reservation.datatables.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    final DAOService daoService;

    public RoleService(DAOService daoService) {
        this.daoService = daoService;
    }

    public boolean isExist(String name) {
        return name.isEmpty() ? false : (Role)daoService.findObject("from Role where rolename='"+name+"'") != null;
    }

    public boolean isExist(Role role) {
        return role!=null ? isExist(role.getRoleName()) : false;
    }

    public boolean isExist(Long id) {
        return getById(id) != null;
    }

    public boolean save(Role role){
        if(!isExist(role) && !role.getRoleName().isEmpty()) {
            daoService.save(role);
            return true;
        }
        return false;
    }

    public boolean update(Role role) {
        if( role!=null && getById(role.getId()) != null  && !role.getRoleName().isEmpty()) {
            daoService.update(role);
            return true;
        }
        return false;
    }

    public boolean delete(Long id) {
        Role role = getById(id);
        if(role == null ) return false;
        daoService.delete(role);
        return true;
    }

    public Role getByRoleName(String name){
        return name.isEmpty()? null : (Role)daoService.findObject("from Role where rolename='"+name+"'");
    }

    public Role getById(Long id){
        return id > 0 ? (Role)daoService.findObject("from Role where id="+id) : null;
    }

    public List<Role> getAll(){
        return (List<Role>)daoService.findAll(Role.class);
    }
}
