package org.library.com.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.library.com.entity.Authors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Aditya
 * Databse Logic Code class
 */
@Repository
public class AuthorsDao {

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
     * This method used for save Authors Details into Database
     *
     * @param authors Authors Object
     */
    public void saveAuthorsDetails(Authors authors) {
        try {
            session = getSession();
            Transaction transaction = session.beginTransaction();
            session.save(authors);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method used for get Authors Object from Database by author name and mail
     *
     * @param name Author name
     * @param mail Author mail
     * @return Author Object
     */
    @SuppressWarnings("unchecked")
    public Authors getAuthorPresent(String name, String mail) {
        Authors authors = null;
        try {
            session = getSession();
            String hql = "from " + Authors.class.getName() + " where author_name=:nm and author_mail=:ml";
            Query<Authors> query = session.createQuery(hql);
            query.setParameter("nm", name).setParameter("ml", mail);
            authors = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return authors;
    }


    /**
     * This method used for get Authors Id from Database by Author Name
     *
     * @param name Author name
     * @return Author Id
     */
    @SuppressWarnings("unchecked")
    public long getAuthorIdByName(String name) {
        long id = 0;
        try {
            session = getSession();
            String hql = "from " + Authors.class.getName() + " where author_name=:nm";
            Query<Authors> query = session.createQuery(hql);
            query.setParameter("nm", name);
            Authors authors = query.uniqueResult();
            id = authors.getId();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return id;
    }


    /**
     * This method used for get Authors mail from Database by author name
     *
     * @param name Author name
     * @return Author Mail
     */
    @SuppressWarnings("unchecked")
    public String getAuthorMail(String name) {
        String mail = null;
        try {
            session = getSession();
            String hql = "from " + Authors.class.getName() + " where author_name=:nm";
            Query<Authors> query = session.createQuery(hql);
            query.setParameter("nm", name);
            Authors authors = query.uniqueResult();
            mail = authors.getAuthor_mail();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return mail;
    }


    /**
     * This method used for get All Authors List  from Database
     *
     * @return List Of Authors
     */
    @SuppressWarnings("unchecked")
    public List<Authors> getAllAuthorsDetails() {
        List<Authors> authorsList = null;
        try {
            session = getSession();
            String hql = "from " + Authors.class.getName();
            Query<Authors> query = session.createQuery(hql);
            authorsList = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return authorsList;
    }

    /**
     * For getting Author Object by Author name
     *
     * @param name String Author name
     * @return Author Object
     */
    @SuppressWarnings("unchecked")
    public Authors getAuthorByName(String name) {
        Authors authors = null;
        try {
            session = getSession();
            Query<Authors> query = session.createQuery("from " + Authors.class.getName()
                    + " where author_name=:nm");
            query.setParameter("nm", name);
            authors = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return authors;
    }

    /**
     * Get Author Object by its Id
     *
     * @param id Author Id
     * @return Author Object
     */
    @SuppressWarnings("unchecked")
    public Authors getAuthorById(long id) {
        Authors authors = null;
        try {
            session = getSession();
            String hql = "from " + Authors.class.getName() + " where id=:idq";
            Query<Authors> query = session.createQuery(hql);
            query.setParameter("idq", id);
            authors = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return authors;
    }
}
