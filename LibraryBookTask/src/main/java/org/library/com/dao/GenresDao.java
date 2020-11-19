package org.library.com.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.library.com.entity.Genres;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Aditya
 * Databse Logic Code class
 */
@Repository
public class GenresDao {

    /**
     * JPA allows to map application classes to tables in database.
     * Entity Manager - Once the mappings are defined, entity manager can manage my entities.
     * Entity Manager handles all interactions with the database
     */
    @Autowired
    private EntityManager entityManager;

    /**
     * unwrap() gives me the Session Object
     *
     * @return Session Object
     */
    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    Session session = null;

    /**
     * This method used for save Genres Object into database
     *
     * @param genres genres Object
     */
    public void saveGenresDetails(Genres genres) {
        try {
            session = getSession();
            Transaction transaction = session.beginTransaction();
            session.save(genres);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method used for get Genres Object from database by genres name
     *
     * @param name genres name
     * @return genres object
     */
    @SuppressWarnings("unchecked")
    public Genres getGenresByName(String name) {
        Genres genres = null;
        try {
            session = getSession();
            String hql = "from " + Genres.class.getName() + " where genres_name=:nm";
            Query<Genres> query = session.createQuery(hql);
            query.setParameter("nm", name);
            genres = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return genres;
    }

    /**
     * This method used for get Genres Object from database
     * by genres name and it's description
     *
     * @param name        genres name
     * @param genres_desc genres description
     * @return genres object
     */
    @SuppressWarnings("unchecked")
    public Genres getGenresByName(String name, String genres_desc) {
        Genres genres = null;
        try {
            session = getSession();
            String hql = "from " + Genres.class.getName() + " where genres_name=:nm and genres_desc=:ml";
            Query<Genres> query = session.createQuery(hql);
            query.setParameter("nm", name).setParameter("ml", genres_desc);
            genres = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return genres;
    }

    /**
     * This method used for get Genres Id  from database by genres name
     *
     * @param name genres name
     * @return genres id
     */
    @SuppressWarnings("unchecked")
    public long getGenIdByName(String name) {
        long id = 0;
        try {
            session = getSession();
            String hql = "from " + Genres.class.getName() + " where genres_name=:nm";
            Query<Genres> query = session.createQuery(hql);
            query.setParameter("nm", name);
            Genres genres = query.uniqueResult();
            if (genres != null) id = genres.getId();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * This method used for retrive all Genres Object from database
     *
     * @return List of Genres
     */
    @SuppressWarnings("unchecked")
    public List<Genres> getAllGenres() {
        List<Genres> listGen = null;
        try {
            session = getSession();
            String hql = "from " + Genres.class.getName();
            Query<Genres> query = session.createQuery(hql);
            listGen = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return listGen;
    }
}
