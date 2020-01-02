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

    private String reservation_name;
    private Timestamp time;

    @ManyToOne (targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    @OneToOne(targetEntity = MeetingRoom.class, fetch = FetchType.LAZY)
    private MeetingRoom meetingRoom;
}
