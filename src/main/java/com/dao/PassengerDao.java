package com.dao;

import com.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PassengerDao extends GeneralDao<Passenger> {

    public PassengerDao() {
        super(Passenger.class);
    }

    String REGULAR_PASSENGER_REQ = "SELECT P FROM Passenger P " +
            "INNER JOIN P.flights F " +
            "WHERE YEAR(F.dateFlight) = :YEAR " +
            "GROUP BY P.id, P.lastName, P.nationality, P.dateOfBirth, P.passportCode " +
            "HAVING COUNT (F) > 5";

    public List<Passenger> getRegularPassengers(int year) {

        return entityManager.createQuery(REGULAR_PASSENGER_REQ, Passenger.class)
                .setParameter("YEAR", year)
                .getResultList();
    }
}
