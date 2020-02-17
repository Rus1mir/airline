package com.controller;

import com.model.Filter;
import com.model.Flight;
import com.service.FlightService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/flight")
public class FlightController {

    private FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    //CRUD
    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity<Flight> save(@RequestBody Flight flight) {

        return new ResponseEntity<>(flightService.save(flight), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find")
    public ResponseEntity<Flight> findById(@RequestParam(value = "id") Long id) throws Exception {

        Flight flight = flightService.findById(id);
        if (flight == null)
            throw new NotFoundException("Flight id: " + id + " was not found");
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update")
    public ResponseEntity<Flight> update(@RequestBody Flight flight) throws Exception {

        if (flightService.findById(flight.getId()) == null)
            throw new NotFoundException("Flight id: " + flight.getId() + " was not found");

        return new ResponseEntity<>(flightService.update(flight), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/remove")
    public ResponseEntity<String> delete(Long id) throws Exception {

        flightService.remove(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    //business logic
    @RequestMapping(method = RequestMethod.GET, value = "/ratingFrom")
    public ResponseEntity<List<String>> mostPopularFrom() {

        List<String> rating = flightService.mostPopularFrom();

        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ratingTo")
    public ResponseEntity<List<String>> mostPopularTo() throws Exception {

        List<String> rating = flightService.mostPopularTo();

        if (rating.size() == 0)
            throw new NotFoundException("No flights found");
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/findByFilter")
    public ResponseEntity<List<Flight>> flightsByDate(@RequestBody Filter filter) throws Exception {

        List<Flight> flights = flightService.flightsByDate(filter);
        if (flights.size() == 0)
            throw new NotFoundException("Flights was not found");
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/filter")
    public ResponseEntity<Filter> findById() {

        return new ResponseEntity<>(new Filter(), HttpStatus.OK);
    }

    //ex handler
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> error(DataIntegrityViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> error(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> error(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
