package com.mokhovav.meeting_room_reservation.datatables;

import javax.persistence.*;

@Entity
@Table(name = "meetingrooms")
public class MeetingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String roomName;
    private Integer capacity;

    public MeetingRoom() {
    }
    public MeetingRoom(String roomName, Integer capacity) {
        this.roomName = roomName;
        this.capacity = capacity;
    }

    @OneToOne(mappedBy = "meetingRoom")     // Bidirectional communication
    private Reservation reservation;

    public Long getId() {
        return id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
