package com.service;

import com.dao.FlightDao;
import com.model.Filter;
import com.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    private FlightDao flightDao;

    @Autowired
    public FlightService(FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    //CRUD
    public Flight save(Flight flight) {
        return flightDao.saveItem(flight);
    }

    public void remove(long id) throws Exception {
        flightDao.removeItem(id);
    }

    public Flight update(Flight flight) {
        return flightDao.update(flight);
    }

    public Flight findById(long id) {
        return flightDao.findById(id);
    }

    //business logic
    public List<String> getRatingFrom() {
        return flightDao.getRatingFrom();
    }

    public List<String> getRatingTo() {
        return flightDao.getRatingTo();
    }

    public List<Flight> getFlightsByDate(Filter filter) {

        return flightDao.getFlightsByDate(filter);
    }
}
