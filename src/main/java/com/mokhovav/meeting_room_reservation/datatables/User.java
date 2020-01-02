package com.mokhovav.meeting_room_reservation.datatables;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String user_name;
    private String password;
    private boolean active;
    private boolean change_password;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "users_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")           // Bidirectional communication
    private Set<Reservation> reservations;

    @OneToMany(mappedBy = "author")         // Bidirectional communication
    private Set<Message> user_messages;

    @ManyToMany(mappedBy = "addressees")    // Bidirectional communication
    private Set<Message> receive_messages;

    public User() {
    }
    public User(String user_name, String password) {
        this.user_name = user_name;
        this.password = password;
        active = true;
        change_password = true;
    }

    public Long getId() {
        return id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public boolean isChange_password() {
        return change_password;
    }

    public void setChange_password(boolean change_password) {
        this.change_password = change_password;
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

    public Set<Message> getUser_messages() {
        return user_messages;
    }

    public void setUser_messages(Set<Message> user_messages) {
        this.user_messages = user_messages;
    }

    public Set<Message> getReceive_messages() {
        return receive_messages;
    }

    public void setReceive_messages(Set<Message> receive_messages) {
        this.receive_messages = receive_messages;
    }
}
