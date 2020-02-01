package com.dao;

import com.model.Plane;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class PlaneDao extends GeneralDao<Plane> {

    public PlaneDao() {
        super(Plane.class);
    }

    private final String OLD_PLANES_REQ = "FROM Plane p WHERE p.yearProduced < :DATE";

    private final String REGULAR_PLANES_REG = "SELECT P FROM Flight F " +
            "INNER JOIN F.plane P " +
            "WHERE YEAR(F.dateFlight) = :YEAR " +
            "GROUP BY P.id, P.avgFuelConsumption, P.code, P.model, P.yearProduced " +
            "HAVING COUNT(F) > 25";

    public List<Plane> oldPlanes() {

        Date date = java.sql.Date.valueOf(LocalDate.now().minusYears(20));

        return entityManager.createQuery(OLD_PLANES_REQ, Plane.class).
                setParameter("DATE", date).
                getResultList();
    }

    public List<Plane> regularPlanes(int year) {

        return entityManager.createQuery(REGULAR_PLANES_REG, Plane.class).
                setParameter("YEAR", year).
                getResultList();
    }
}
