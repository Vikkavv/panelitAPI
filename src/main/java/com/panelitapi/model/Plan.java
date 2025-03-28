package com.panelitapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "plan")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @NotNull
    @Column(name = "n_max_panels", nullable = false)
    private Integer nMaxPanels;

    @NotNull
    @Column(name = "n_max_Collaborators", nullable = false)
    private Integer nMaxCollaborators;

    @Column(name = "month_price", columnDefinition = "double UNSIGNED not null")
    private Object monthPrice;

    @Column(name = "year_price", columnDefinition = "double UNSIGNED not null")
    private Object yearPrice;

    @OneToMany(mappedBy = "plan")
    @JsonIgnoreProperties({"plan"})
    private Set<User> users = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNMaxPanels() {
        return nMaxPanels;
    }

    public void setNMaxPanels(Integer nMaxPanels) {
        this.nMaxPanels = nMaxPanels;
    }

    public Integer getNMaxCollaborators() {
        return nMaxCollaborators;
    }

    public void setNMaxCollaborators(Integer nMaxCollaborators) {
        this.nMaxCollaborators = nMaxCollaborators;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Integer getnMaxPanels() {
        return nMaxPanels;
    }

    public void setnMaxPanels(Integer nMaxPanels) {
        this.nMaxPanels = nMaxPanels;
    }

    public Object getYearPrice() {
        return yearPrice;
    }

    public void setYearPrice(Object yearPrice) {
        this.yearPrice = yearPrice;
    }

    public Object getMonthPrice() {
        return monthPrice;
    }

    public void setMonthPrice(Object monthPrice) {
        this.monthPrice = monthPrice;
    }

    public Integer getnMaxCollaborators() {
        return nMaxCollaborators;
    }

    public void setnMaxCollaborators(Integer nMaxCollaborators) {
        this.nMaxCollaborators = nMaxCollaborators;
    }
}