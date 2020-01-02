package com.mokhovav.meeting_room_reservation.datatables;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String role_name;

    @ManyToMany(mappedBy = "roles")         // Bidirectional communication
    private Set<User> users;

    public Role() {
    }

    public Role(String role_name) {
        this.role_name = role_name;
    }

    public Long getId() {
        return id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
