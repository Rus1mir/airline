package com.dao;

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

    private CriteriaBuilder criteriaBuilder;

    public List<String> mostPopularFrom() {

        return entityManager.createQuery(RATING_FROM_REQ, String.class).
                setMaxResults(10).
                getResultList();
    }

    public List<String> mostPopularTo() {

        return entityManager.createQuery(RATING_TO_REQ, String.class).
                setMaxResults(10).
                getResultList();
    }

    public ArrayList<Flight> flightsByDate(Filter filter) {

        criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flight> flightCriteria = criteriaBuilder.createQuery(Flight.class);
        Root<Flight> from = flightCriteria.from(Flight.class);
        Join<Flight, Plane> planeJoin = from.join("plane");

        Predicate predicate = andBetweenDate(criteriaBuilder.conjunction(),
                from.get("dateFlight"), filter.getDateAfter(), filter.getDateBefore());

        predicate = andEqual(predicate, planeJoin.get("model"), filter.getPlaneName());

        predicate = andEqual(predicate, from.get("cityFrom"), filter.getCityDepart());

        predicate = andEqual(predicate, from.get("cityTo"), filter.getCityDest());

        flightCriteria.select(from).where(predicate);
        return (ArrayList<Flight>) entityManager.createQuery(flightCriteria).getResultList();
    }

    private Predicate andBetweenDate(Predicate p, Expression<Date> ex, Date d1, Date d2) {

        if (d1 != null)
            return criteriaBuilder.and(p, criteriaBuilder.between(ex, d1, d2 == null ? addDays(d1, 1) : d2));
        return p;
    }

    private Predicate andEqual(Predicate p, Expression<?> ex, Object o) {

        if (o != null)
             criteriaBuilder.equal(ex, o);
        return p;
    }

    private Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }
}
