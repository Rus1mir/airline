package com.controller;

import com.model.Plane;
import com.service.PlaneService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/plane")
public class PlaneController {
    private PlaneService planeService;

    @Autowired
    public PlaneController(PlaneService planeService) {
        this.planeService = planeService;
    }

    //CRUD
    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity<Plane> save(@RequestBody Plane flight) {

        return new ResponseEntity<>(planeService.save(flight), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find")
    public ResponseEntity<Plane> findById(@RequestParam(value = "id") Long id) throws Exception {

        Plane flight = planeService.findById(id);
        if (flight == null)
            throw new NotFoundException("Plane id: " + id + " was not found");
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update")
    public ResponseEntity<Plane> update(@RequestBody Plane flight) throws Exception {

        if (planeService.findById(flight.getId()) == null)
            throw new NotFoundException("Plane id: " + flight.getId() + " was not found");

        return new ResponseEntity<>(planeService.update(flight), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/remove")
    public ResponseEntity<String> delete(Long id) throws Exception {

        planeService.remove(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    //business
    @RequestMapping(method = RequestMethod.GET, value = "/findOld")
    public ResponseEntity<List<Plane>> oldPlanes() throws Exception {

        List<Plane> planes = planeService.oldPlanes();
        if (planes.size() == 0)
            throw new NotFoundException("Plane oldest when 20 years old was not found");
        return new ResponseEntity<>(planes, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findRegular")
    public ResponseEntity<List<Plane>> regularPlanes(@RequestParam(value = "year") int year) throws Exception {

        List<Plane> planes = planeService.regularPlanes(year);
        if (planes.size() == 0)
            throw new NotFoundException("Planes with over 25 flights per year: " + year + " not found");
        return new ResponseEntity<>(planes, HttpStatus.OK);
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
