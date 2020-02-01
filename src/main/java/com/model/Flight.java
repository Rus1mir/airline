package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;


@Entity
@Table(name = "FLIGHT")
public class Flight {

    private Long id;
    private Plane plane;
    private List<Passenger> passengers = new ArrayList<>();
    private Date dateFlight;
    private String cityFrom;
    private String cityTo;

    public Flight(Plane plane, List<Passenger> passengers, Date dateFlight, String cityFrom, String cityTo) {
        this.plane = plane;
        this.passengers = passengers;
        this.dateFlight = dateFlight;
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
    }

    public Flight() {
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "F_SEQ", sequenceName = "FLIGHT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "F_SEQ")
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "PLANE_ID")
    public Plane getPlane() {
        return plane;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "PASSENGER_FLIGHT",
            joinColumns = { @JoinColumn(name = "FLIGHT_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "PASS_ID", nullable = false, updatable = false) })
    @JsonIgnore
    public List<Passenger> getPassenger() {
        return passengers;
    }

    @Column(name = "DATE_FLIGHT")
    public Date getDateFlight() {
        return dateFlight;
    }

    @Column(name = "CITY_FROM")
    public String getCityFrom() {
        return cityFrom;
    }

    @Column(name = "CITY_TO")
    public String getCityTo() {
        return cityTo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public void setPassenger(List<Passenger> passenger) {
        this.passengers = passenger;
    }

    public void setDateFlight(Date dateFlight) {
        this.dateFlight = dateFlight;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }
}
