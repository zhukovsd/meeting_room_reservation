package com.mokhovav.meeting_room_reservation.configurations;

import com.mokhovav.meeting_room_reservation.datatables.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class HibernateConfiguration  {

    private static SessionFactory sessionFactory;
    private HibernateConfiguration() {
    }

    @Bean
    public static SessionFactory getSessionFactory() throws Exception {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.xml");
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Role.class);
                configuration.addAnnotatedClass(Reservation.class);
                configuration.addAnnotatedClass(Message.class);
                configuration.addAnnotatedClass(MeetingRoom.class);
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                System.out.println("Exception!" + e);
            }
        }
        return sessionFactory;
    }
}
