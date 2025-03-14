package com.panelitapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 25)
    @NotNull
    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Size(max = 25)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 25)
    private String lastName;

    @Size(max = 20)
    @NotNull
    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Size(max = 70)
    @NotNull
    @Column(name = "email", nullable = false, length = 70)
    private String email;

    @Size(max = 30)
    @NotNull
    @Column(name = "password", nullable = false, length = 30)
    private String password;

    @Size(max = 20)
    @Column(name = "phone_Number", length = 20)
    private String phoneNumber;

    @Lob
    @Column(name = "profile_picture")
    private String profilePicture;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Column(name = "plan_expiration_date")
    private Instant planExpirationDate;

    @OneToMany(mappedBy = "integrant")
    private Set<ChatIntegrant> chatIntegrants = new LinkedHashSet<>();

    @ManyToMany
    private Set<User> users = new LinkedHashSet<>();

    @OneToMany(mappedBy = "sender")
    private Set<Message> messages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<Note> notes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "receiver")
    private Set<Notification> notifications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "participant")
    private Set<PanelParticipant> panelParticipants = new LinkedHashSet<>();

    @ManyToMany
    private Set<Panel> panels = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Instant getPlanExpirationDate() {
        return planExpirationDate;
    }

    public void setPlanExpirationDate(Instant planExpirationDate) {
        this.planExpirationDate = planExpirationDate;
    }

    public Set<ChatIntegrant> getChatIntegrants() {
        return chatIntegrants;
    }

    public void setChatIntegrants(Set<ChatIntegrant> chatIntegrants) {
        this.chatIntegrants = chatIntegrants;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Set<PanelParticipant> getPanelParticipants() {
        return panelParticipants;
    }

    public void setPanelParticipants(Set<PanelParticipant> panelParticipants) {
        this.panelParticipants = panelParticipants;
    }

    public Set<Panel> getPanels() {
        return panels;
    }

    public void setPanels(Set<Panel> panels) {
        this.panels = panels;
    }

}