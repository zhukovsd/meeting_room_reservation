package com.mokhovav.meeting_room_reservation.datatables;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String text;

    @ManyToOne (targetEntity = User.class, fetch = FetchType.LAZY)
    private User author;

    @ManyToMany (targetEntity = User.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "message_address", joinColumns = @JoinColumn(name = "messages_id"))
    private Set<User> addressees;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<User> getAddressees() {
        return addressees;
    }

    public void setAddressees(Set<User> addressees) {
        this.addressees = addressees;
    }
}
