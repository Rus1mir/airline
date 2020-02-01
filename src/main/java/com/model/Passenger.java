package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "PASSENGER")
public class Passenger {

    private Long id;
    private String lastName;
    private String nationality;
    private Date dateOfBirth;
    private String passportCode;
    private List<Flight> flights = new ArrayList<>();

    public Passenger(String lastName, String nationality, Date dateOfBirth, String passportCode, List<Flight> flights) {
        this.lastName = lastName;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        this.passportCode = passportCode;
        this.flights = flights;
    }

    public Passenger() {
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "PASS_SEQ", sequenceName = "PASSENGER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PASS_SEQ")
    public Long getId() {
        return id;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    @Column(name = "NATIONALITY")
    public String getNationality() {
        return nationality;
    }

    @Column(name = "DATE_OF_BIRTH")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    @Column(name = "PASSPORT_CODE")
    public String getPassportCode() {
        return passportCode;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "PASSENGER_FLIGHT",
            joinColumns = { @JoinColumn(name = "PASS_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "FLIGHT_ID", nullable = false, updatable = false)})
    @JsonIgnore
    public List<Flight> getFlights() {
        return flights;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPassportCode(String passportCode) {
        this.passportCode = passportCode;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}
