package com.dev.cinema.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import com.dev.cinema.dao.RoleDao;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.Role;

@Repository
public class RoleDaoImpl implements RoleDao {
    private final SessionFactory sessionFactory;

    public RoleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Role role) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession().getSession();
            transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
        } catch (DataProcessingException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add role " + role, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Role findByName(String roleName) {
        try (Session session = sessionFactory.openSession().getSession()) {
            Query<Role> findByNameQuery = session.createQuery("from Role r "
                    + "where r.roleName = :roleName", Role.class);
            findByNameQuery.setParameter("roleName", Role.of(roleName).getRoleName());
            return findByNameQuery.getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find role by name " + roleName, e);
        }
    }
}
