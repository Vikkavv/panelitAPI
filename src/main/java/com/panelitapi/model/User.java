package com.panelitapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 25)
    @NotNull(message = "Field name can not be null")
    @Column(name = "name", nullable = false, length = 25)
    @Pattern(regexp = "[A-Za-zÑñÁÉÍÓÚÇáéíóúçÀÈÌÒÙàèìòùÂÊÎÔÛâêîôûÄËÏÖÜäëïöü]{1,}", message = "Name can not take special symbols and either numbers.")
    private String name;

    @Size(max = 25)
    @NotNull(message = "Field last name can not be null")
    @Column(name = "last_name", nullable = false, length = 25)
    @Pattern(regexp = "[A-Za-zÑñÁÉÍÓÚÇáéíóúçÀÈÌÒÙàèìòùÂÊÎÔÛâêîôûÄËÏÖÜäëïöü]{1,}", message = "Last name can not take special symbols and either numbers.")
    private String lastName;

    @Size(max = 20)
    @NotNull(message = "Field nickname can not be null")
    @Column(name = "nickname", nullable = false, length = 20)
    @Pattern(regexp = "[-A-Za-z0-9ÑñÁÉÍÓÚÇáéíóúçÀÈÌÒÙàèìòùÂÊÎÔÛâêîôûÄËÏÖÜäëïöü_/\\\\.|]{6,25}", message = "Nickname must be at least 6 characters long, include numbers, and common symbols and letters.")
    private String nickname;

    @Size(max = 70)
    @NotNull(message = "Field email can not be null")
    @Column(name = "email", nullable = false, length = 70)
    @Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]+(([.][a-z]+)*)[.][a-z]{2,}", message = "Use a valid email format")
    private String email;

    @Size(max = 60)
    @NotNull(message = "Field password can not be null")
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
    @JsonIgnoreProperties({"users", "hibernateLazyInitializer", "handler"})
    private Plan plan;

    @Column(name = "plan_expiration_date")
    private LocalDateTime planExpirationDate;

    @OneToMany(mappedBy = "integrant")
    private Set<ChatIntegrant> chatsBelonged = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable( name = "followers", joinColumns = @JoinColumn(name = "follower_id"), inverseJoinColumns = @JoinColumn(name = "followed_id"))
    @JsonIgnore
    private Set<User> followers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "sender")
    @JsonIgnoreProperties({"sender", "hibernateLazyInitializer", "handler"})
    private Set<Message> messages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "owner")
    @JsonIgnoreProperties({"owner", "hibernateLazyInitializer", "handler"})
    private Set<Note> notes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "receiver")
    @JsonIgnoreProperties({"receiver", "hibernateLazyInitializer", "handler"})
    private Set<Notification> notifications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "participant")
    @JsonIgnoreProperties({"participant", "hibernateLazyInitializer", "handler"})
    private Set<PanelParticipant> panelParticipants = new LinkedHashSet<>();

    @OneToMany(mappedBy = "subscriber")
    @JsonIgnoreProperties({"subscriber", "hibernateLazyInitializer", "handler"})
    private Set<PanelSubscriber> subscribedPanels = new LinkedHashSet<>();

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

    public LocalDateTime getPlanExpirationDate() {
        return planExpirationDate;
    }

    public void setPlanExpirationDate(LocalDateTime planExpirationDate) {
        this.planExpirationDate = planExpirationDate;
    }

    public Set<ChatIntegrant> getChatChatsBelonged() {
        return chatsBelonged;
    }

    public void setChatChatsBelonged(Set<ChatIntegrant> chatsBelonged) {
        this.chatsBelonged = chatsBelonged;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
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

    public Set<PanelSubscriber> getPanels() {
        return subscribedPanels;
    }

    public void setPanels(Set<PanelSubscriber> subscribedPanels) {
        this.subscribedPanels = subscribedPanels;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", plan=" + plan +
                ", planExpirationDate=" + planExpirationDate +
                '}';
    }
}