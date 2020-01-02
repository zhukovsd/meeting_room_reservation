package com.mokhovav.meeting_room_reservation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class LoggingController {
    @Bean
    public static Logger getLogger() {
        return LoggerFactory.getLogger(LoggingController.class);
    }
}
