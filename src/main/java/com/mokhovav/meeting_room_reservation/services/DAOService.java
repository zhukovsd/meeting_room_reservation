package com.mokhovav.meeting_room_reservation.services;

import com.mokhovav.meeting_room_reservation.configurations.HibernateConfiguration;
import com.mokhovav.meeting_room_reservation.datatables.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DAOService<T> implements DAO<T> {

    final SessionFactory sessionFactory;

    public DAOService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(T object) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(object);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(T object) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(object);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(T object) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(object);
        transaction.commit();
        session.close();
    }

    @Override
    public List<T> getBy(Class c, String field, Object value) {
        return (List<T>) sessionFactory.openSession().createQuery("From "+c.getName()+" where "+field+"="+value).list();
    }

    @Override
    public List<T> findObjects(Class c) {
        return (List<T>) sessionFactory.openSession().createQuery("From "+ c.getName()).list();
    }
}
