package com.mokhovav.meeting_room_reservation.services;

import org.springframework.stereotype.Service;

@Service
public class UserService<T> {
    private final DAOService<T> daoService;

    public UserService(DAOService<T> daoService) {
        this.daoService = daoService;
    }

    public void save(T object) {
        daoService.save(object);

    }
}
