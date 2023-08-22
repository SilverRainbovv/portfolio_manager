package com.didenko.dao;

import com.didenko.util.HibernateUtil;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseDao<K extends Serializable, E> implements Dao<K, E>{

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final Class<E> entityClass;

    @Override
    public E save(E entity) {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();

        return entity;
    }

    @Override
    public Optional<E> findById(K id) {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var entity = Optional.ofNullable(session.get(entityClass, id));
        session.getTransaction().commit();

        return entity;
    }

    @Override
    public List<E> findAll() {
        return null;
    }

    @Override
    public void update(E entity) {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(entity);
        session.getTransaction().commit();
    }

    @Override
    public void delete(K id) {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var entity= session.get(entityClass, id);
        session.remove(entity);
        session.getTransaction().commit();
    }
}
