package com.service;

import com.dao.PassengerDao;
import com.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    private PassengerDao passengerDao;

    @Autowired
    public PassengerService(PassengerDao passengerDao) {
        this.passengerDao = passengerDao;
    }

    //CRUD
    public Passenger save(Passenger plane) {
        return passengerDao.saveItem(plane);
    }

    public void remove(long id) throws Exception {
        passengerDao.removeItem(id);
    }

    public Passenger update(Passenger flight) {
        return passengerDao.update(flight);
    }

    public Passenger findById(long id) {
        return passengerDao.findById(id);
    }

    //business logic
    public List<Passenger> getRegularPassengers(int year) {

        return passengerDao.getRegularPassengers(year);
    }
}
