package com.mokhovav.meeting_room_reservation.datatables;

import javax.persistence.*;

@Entity
@Table(name = "meetingrooms")
public class MeetingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String room_name;
    private Integer capasity;

    public MeetingRoom() {
    }
    public MeetingRoom(String room_name, Integer capasity) {
        this.room_name = room_name;
        this.capasity = capasity;
    }

    @OneToOne(mappedBy = "meetingRoom")     // Bidirectional communication
    private Reservation reservation;
}
