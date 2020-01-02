package com.mokhovav.meeting_room_reservation.datatables;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String reservationName;
    private Timestamp time;

    @ManyToOne (targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    @OneToOne(targetEntity = MeetingRoom.class, fetch = FetchType.LAZY)
    private MeetingRoom meetingRoom;

    public Long getId() {
        return id;
    }

    public String getReservationName() {
        return reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(MeetingRoom meetingRoom) {
        this.meetingRoom = meetingRoom;
    }
}
