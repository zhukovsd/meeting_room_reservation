package com.mokhovav.meeting_room_reservation.controllers;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    final Logger logger;

    public ExceptionController(Logger logger) {
        this.logger = logger;
    }

    @ExceptionHandler(Exception.class)
    private void handleException(Exception exception){
        logger.error("Exception: "+exception.getMessage());
    }
}
