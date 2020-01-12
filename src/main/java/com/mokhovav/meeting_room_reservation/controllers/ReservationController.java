package com.mokhovav.meeting_room_reservation.controllers;

import com.mokhovav.meeting_room_reservation.datatables.Reservation;
import com.mokhovav.meeting_room_reservation.datatables.User;
import com.mokhovav.meeting_room_reservation.responses.DailySchedule;
import com.mokhovav.meeting_room_reservation.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;

@Controller
@RequestMapping("/conferences")
public class ReservationController {
    @Autowired
    Logger logger;

    final ReservationService reservationService;

    private int startWeak = 0;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public String conferences(@AuthenticationPrincipal User user, Model model){
        List<DailySchedule> list = reservationService.getSchedule(startWeak);
        model.addAttribute("schedule",list);

        List<String> time = new ArrayList<>();
        for (int i = 0 ; i<= 24; i++)
            time.add(String.format("%02d",i)+":00");
        model.addAttribute("time", time);
        int temp = 0;
        model.addAttribute("temp",temp);

        return "conferences";
    }

    @GetMapping("prevWeek")
    public String prevWeek(Model model){
        startWeak--;
        return "redirect:/conferences";
    }
    @GetMapping("nextWeek")
    public String nextWeek(Model model){
        startWeak++;
        return "redirect:/conferences";
    }

    @GetMapping("addReservation")
    public String addConference(Model model){
        model.addAttribute("isAdd", true);
        return "addreservation";
    }
    @GetMapping("showReservation/{reservationId}")
    public String showConference(@PathVariable Long reservationId, Model model){
        Reservation reservation = reservationService.getById(reservationId);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");

        model.addAttribute("title",reservation.getTitle());
        model.addAttribute("description", reservation.getDescription());
        model.addAttribute("date", dateFormat.format(reservation.getTimeBegin()));
        model.addAttribute("time", timeFormat.format(reservation.getTimeBegin()));
        model.addAttribute("duration", (reservation.getTimeEnd() - reservation.getTimeBegin())/60000);
        model.addAttribute("isAdd", false);

        return "addreservation";
    }


    @PostMapping("addReservation")
    public String addConference(
            @AuthenticationPrincipal User user,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Integer duration,
            @RequestParam String date,
            @RequestParam String time,
            Model model){
        model.addAttribute("isAdd", true);
        //logger.info("Time: " + time);
        if (!reservationService.isValid(user,title,description,duration,date,time)) {
            model.addAttribute("message", "Invalid data");
            return "addreservation";
        }
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setTitle(title);
        reservation.setDescription(description);
        //reservation.setDuration(duration);
        reservation.setTimeBegin(Timestamp.valueOf(date + " " + time + ":00").getTime());
        reservation.setTimeEnd(reservationService.getTimeEnd(reservation.getTimeBegin(),duration));
    /*
        logger.info("*****************************************************************************************");
        logger.info(reservation.getUser().getUserName());
        logger.info(reservation.getTitle());
        logger.info(reservation.getDescription());
        logger.info(String.valueOf(reservation.getTimeBegin()));
        logger.info(String.valueOf(reservation.getTimeEnd()));
        logger.info("*****************************************************************************************");
    */
        if (reservationService.isCanBeBooked(reservation)){
            reservationService.save(reservation);
            model.addAttribute("message", "Success!");
        }
        else
            model.addAttribute("message", "Meeting room busy at this time!");
            model.addAttribute("title", reservation.getTitle());
            model.addAttribute("description", reservation.getDescription());
        return "addreservation";
    }


}
