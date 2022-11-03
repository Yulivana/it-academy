package com.pvt.dao;

import com.pvt.connector.HibernateUtil;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class AbstractJPADAO<T, K> implements Serializable {

    private static final long serialVersionUID = 1L;

    private SessionFactory sessionFactory = HibernateUtil.getSessionJavaConfigFactory();
    private Session session;
    private Transaction transaction;


    private Class<T> entityClass;

    public AbstractJPADAO(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.session = this.session != null && this.session.isOpen() ? sessionFactory.getCurrentSession() : sessionFactory.openSession();
    }

    public void beginTransaction() {
        try {
            transaction = session.beginTransaction();
        } catch (IllegalStateException e) {
            rollbackTransaction();
        }
    }

    public void commit() {
        try {
            transaction.commit();
        } catch (IllegalStateException e) {
            rollbackTransaction();
        }
    }

    private void rollbackTransaction() {
        try {
            transaction.rollback();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void closeSession() {
        session.clear();
        session.close();
    }

    public void flush() {
        session.flush();
    }

    public void save(T entity) {
        session.save(entity);
    }

    public void delete(K id) {
        T entityToBeRemoved = session.find(entityClass, id);
        session.delete(entityToBeRemoved);
    }

    public void update(T entity) {
        session.merge(entity);
    }

    public T find(K id) {
        T result = session.find(entityClass, id);
        session.refresh(result);
        return result;
    }

    public T findReferenceOnly(K id) {
        return session.getReference(entityClass, id);
    }

    public List<T> findAll(String nq) {
        TypedQuery<T> namedQuery = session.createNamedQuery(nq, entityClass);
        List<T> result = namedQuery.getResultList();
        result.forEach(r -> session.refresh(r));
        return result;
    }

    protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
        T result = null;

        try {
            TypedQuery<T> query = session.createNamedQuery(namedQuery, entityClass);
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(query, parameters);
            }

            result = query.getSingleResult();

        } catch (NoResultException e) {
            System.out.println("No result found for named query: " + namedQuery);
        } catch (Exception e) {
            System.out.println("Error while running query: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    private void populateQueryParameters(Query query, Map<String, Object> parameters) {
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
}
