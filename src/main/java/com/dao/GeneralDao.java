package com.dao;

import javassist.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public abstract class GeneralDao<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<T> cl;

    public GeneralDao(Class<T> cl) {
        this.cl = cl;
    }

    public T saveItem(T item) {

        entityManager.persist(item);
        return item;
    }

    public void removeItem(long id) throws Exception {

        T item = entityManager.find(cl, id);

        if (item == null)
            throw new NotFoundException("Item id: " + id + " not found for delete");
        entityManager.remove(item);
    }

    public T update(T item) {
        return entityManager.merge(item);
    }

    public T findById(long id) {
        return entityManager.find(cl, id);
    }
}
