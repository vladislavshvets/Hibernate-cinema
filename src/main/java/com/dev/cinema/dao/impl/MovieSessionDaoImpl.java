package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.MovieSessionDao;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.MovieSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final Logger logger = Logger.getLogger(MovieSessionDaoImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public MovieSessionDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> query = session.createQuery("FROM MovieSession"
                    + " WHERE movie_id =: movieId"
                    + " AND showTime BETWEEN :startOfDay AND :endOfDay", MovieSession.class);
            query.setParameter("movieId", movieId);
            query.setParameter("startOfDay", date.atStartOfDay());
            query.setParameter("endOfDay", date.atTime(LocalTime.MAX));
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Fail! Can't get available sessions: "
                    + movieId + " " + date, e);
        }
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            logger.info(movieSession + " added into the database");
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Fail! Can't add new session: " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public MovieSession findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(MovieSession.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get session by id = " + id, e);
        }
    }
}
