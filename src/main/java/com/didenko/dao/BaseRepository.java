package com.didenko.dao;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public abstract class BaseRepository<K extends Serializable, E> implements Repository<K, E> {

    private SessionFactory sessionFactory;
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
    public List<E> findAll(){
        @Cleanup var session = sessionFactory.openSession();
        var criteria = session.getCriteriaBuilder().createQuery(entityClass);
        var from = criteria.from(entityClass);

        return session.createQuery(criteria).getResultList();
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
