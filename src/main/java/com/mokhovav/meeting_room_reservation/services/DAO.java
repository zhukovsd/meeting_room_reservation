package com.mokhovav.meeting_room_reservation.services;

import java.util.List;
import java.util.Map;

public interface DAO<T> {
    public void save(T object);
    public void update(T object);
    public void delete(T object);
    public T findBy(Class c, String field, Object value);
    public List<T> findAllBy(Class c, String field, Object value);
    public List<T> findAll(Class c);
    public List<T> findAllBy(Class c, Map<String, Object> map);
}
