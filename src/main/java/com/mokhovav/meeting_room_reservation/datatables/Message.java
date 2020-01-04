package com.mokhovav.meeting_room_reservation.datatables;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    @NotBlank(message = "Please fill the message")
    @Length(max = 2048, message = "Message to long (more than 2kB)")
    private String text;

    @ManyToOne (targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn (name = "user_id") //column name
    private User author;

    @ManyToMany (targetEntity = User.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "message_address", joinColumns = @JoinColumn(name = "messages_id"))
    private Set<User> addressees;

    public Message(String title, String text, User author) {
        this.title = title;
        this.text = text;
        this.author = author;
    }

    public Message() {
    }

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
