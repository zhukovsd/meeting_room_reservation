package com.mokhovav.meeting_room_reservation.datatables;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String password;
    private boolean active;
    private boolean changePassword;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "users_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")           // Bidirectional communication
    private Set<Reservation> reservations;

    @OneToMany(mappedBy = "author")         // Bidirectional communication
    private Set<Message> userMessages;

    @ManyToMany(mappedBy = "addressees")    // Bidirectional communication
    private Set<Message> receiveMessages;

    public User() {
    }
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        active = true;
        changePassword = true;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isChangePassword() {
        return changePassword;
    }

    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<Message> getUserMessages() {
        return userMessages;
    }

    public void setUserMessages(Set<Message> userMessages) {
        this.userMessages = userMessages;
    }

    public Set<Message> getReceiveMessages() {
        return receiveMessages;
    }

    public void setReceiveMessages(Set<Message> receiveMessages) {
        this.receiveMessages = receiveMessages;
    }
}
