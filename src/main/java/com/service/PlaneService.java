package com.service;

import com.dao.PlaneDao;
import com.model.Plane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaneService {

    private PlaneDao planeDao;

    @Autowired
    public PlaneService(PlaneDao planeDao) {
        this.planeDao = planeDao;
    }

    //CRUD
    public Plane save(Plane plane) {
        return planeDao.saveItem(plane);
    }

    public void remove(long id) throws Exception {
        planeDao.removeItem(id);
    }

    public Plane update(Plane flight) {
        return planeDao.update(flight);
    }

    public Plane findById(long id) {
        return planeDao.findById(id);
    }

    //business logic
    public List<Plane> getOldPlanes() {

        return planeDao.getOldPlanes();
    }

    public List<Plane> getRegularPlanes(int year) {

        return planeDao.getRegularPlanes(year);
    }
}
