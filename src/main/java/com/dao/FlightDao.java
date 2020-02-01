package com.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Filter;
import com.model.Flight;
import com.model.Plane;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

@Repository
@Transactional
public class FlightDao extends GeneralDao<Flight> {

    public FlightDao() {
        super(Flight.class);
    }

    private final String RATING_TO_REQ = "SELECT F.cityTo FROM Flight F GROUP BY F.cityTo ORDER BY COUNT(F.cityTo)DESC";

    private final String RATING_FROM_REQ = "SELECT F.cityFrom FROM Flight F GROUP BY F.cityFrom ORDER BY COUNT(F.cityFrom)DESC";

    public List<String> getRatingFrom() {

        return entityManager.createQuery(RATING_FROM_REQ, String.class).
                setMaxResults(10).
                getResultList();
    }

    public List<String> getRatingTo() {

        return entityManager.createQuery(RATING_TO_REQ, String.class).
                setMaxResults(10).
                getResultList();
    }

    public ArrayList<Flight> getFlightsByDate(Filter filter) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flight> flightCriteria = criteriaBuilder.createQuery(Flight.class);
        Root<Flight> from = flightCriteria.from(Flight.class);
        Join<Flight, Plane> planeJoin = from.join("plane");
        Predicate predicate = criteriaBuilder.conjunction();

        if(filter.getDateAfter() != null && filter.getDateBefore() == null)
            filter.setDateBefore(addDays(filter.getDateAfter(), 1));

        if (filter.getDateBefore() != null && filter.getDateAfter() != null)
            predicate = criteriaBuilder.and(criteriaBuilder.between
                    (from.get("dateFlight"), filter.getDateAfter(), filter.getDateBefore()));

        if (filter.getPlaneName() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(planeJoin.get("model"), filter.getPlaneName()));

        if (filter.getCityDepart() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(from.get("cityFrom"), filter.getCityDepart()));

        if (filter.getCityDest() != null)
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(from.get("cityTo"), filter.getCityDest()));


        flightCriteria.select(from).where(predicate);
        return (ArrayList<Flight>) entityManager.createQuery(flightCriteria).getResultList();
    }

    private Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }
}
