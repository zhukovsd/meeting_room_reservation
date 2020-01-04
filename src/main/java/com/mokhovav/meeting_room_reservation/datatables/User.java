package com.mokhovav.meeting_room_reservation.datatables;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {      //UserDetails need for login
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Username cannot be empty")
    private String userName;
    @NotBlank(message = "Password cannot be empty")
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

    public boolean isRole(Role role) {
        for (Role r : roles)
            if (r.getRoleName().equals(role.getRoleName()))
                return true;
        return false;
    }

    public boolean isRole(String role) {
        for (Role r : roles)
            if (r.getRoleName().equals(role))
                return true;
        return false;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}
