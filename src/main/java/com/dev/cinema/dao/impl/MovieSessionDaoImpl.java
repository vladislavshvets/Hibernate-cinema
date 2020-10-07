package com.dev.cinema.dao.impl;

import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.dao.MovieSessionDao;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
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
}
