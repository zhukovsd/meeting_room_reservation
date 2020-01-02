package com.mokhovav.meeting_room_reservation.services;

import com.mokhovav.meeting_room_reservation.datatables.Role;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    final DAOService daoService;
    final Logger logger;

    public RoleService(DAOService daoService, Logger logger) {
        this.daoService = daoService;
        this.logger = logger;
    }

    public boolean addRole(String roleName){
        Role roleFromDb = (Role) daoService.findBy(Role.class, "roleName", roleName.toLowerCase());
        if (roleFromDb != null) return false;
        Role role = new Role(roleName.toLowerCase());
        daoService.save(role);
        return true;
    }
}
