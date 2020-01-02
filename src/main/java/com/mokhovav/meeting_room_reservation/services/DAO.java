package com.mokhovav.meeting_room_reservation.services;

import java.util.List;

public interface DAO<T> {
    public void save(T object);
    public void update(T object);
    public void delete(T object);
    public List<T> getBy(Class c, String field, Object value);
    public List<T> findObjects(Class c);
}