package com.mokhovav.meeting_room_reservation.datatables;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "reservations")
public class Reservation implements Comparable<Reservation>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
   // private Timestamp timeBegin;
   // private Timestamp timeEnd;

    private long timeBegin;
    private long timeEnd;

    @ManyToOne (targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    @OneToOne(targetEntity = MeetingRoom.class, fetch = FetchType.LAZY)
    private MeetingRoom meetingRoom;

    public Reservation() {
    }

    public String getUserName() {
        return user.getUserName();
    }

    public Long getId() {
        return id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String content) {
        this.description = content;
    }

   /* public Timestamp getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(Timestamp timeBegin) {
        this.timeBegin = timeBegin;
    }

    public Timestamp getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Timestamp timeEnd) {
        this.timeEnd = timeEnd;
    }*/

    public long getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(long timeBegin) {
        this.timeBegin = timeBegin;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(long timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public int compareTo(Reservation r) {
        return (int)(this.timeBegin - r.getTimeBegin());
    }
}

