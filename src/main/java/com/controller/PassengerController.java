package com.controller;

import com.model.Passenger;
import com.service.PassengerService;
import javassist.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/passenger")
public class PassengerController {

    private PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    //CRUD
    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity<Passenger> save(@RequestBody Passenger passenger) {

        return new ResponseEntity<>(passengerService.save(passenger), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find")
    public ResponseEntity<Passenger> findById(@RequestParam(value = "id") Long id) throws Exception {

        Passenger passenger = passengerService.findById(id);
        if (passenger == null)
            throw new NotFoundException("Passenger id: " + id + " was not found");
        return new ResponseEntity<>(passenger, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update")
    public ResponseEntity<Passenger> update(@RequestBody Passenger flight) throws Exception {

        if (passengerService.findById(flight.getId()) == null)
            throw new NotFoundException("Passenger id: " + flight.getId() + " was not found");

        return new ResponseEntity<>(passengerService.update(flight), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/remove")
    public ResponseEntity<String> delete(Long id) throws Exception {

        passengerService.remove(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    //business logic
    @RequestMapping(method = RequestMethod.GET, value = "/regular")
    public ResponseEntity<List<Passenger>> regularPassengers(@RequestParam(value = "year") int year) throws Exception {

        List<Passenger> passengers = passengerService.regularPassengers(year);
        if (passengers.size() == 0)
            throw new NotFoundException("For year: " + year + " regular passengers was not found");
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }

    //ex handlers
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
