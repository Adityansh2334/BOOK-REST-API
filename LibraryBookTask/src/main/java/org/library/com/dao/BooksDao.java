package org.library.com.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.library.com.entity.Authors;
import org.library.com.entity.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;

/**
 * @author Aditya
 * Databse Logic Code class
 */
@Repository
public class BooksDao {

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
     * This method used for save Books Details into Database
     *
     * @param books Books object
     */
    public void saveBooksDetails(Books books) {
        try {
            session = this.getSession();
            Transaction transaction = session.beginTransaction();
            session.save(books);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method used for get Books List  from Database books Table
     *
     * @return List of Books
     */
    @SuppressWarnings("unchecked")
    public List<Books> getBooksALl() {
        List<Books> resultlist = null;
        try {
            session = getSession();
            String hql = "from " + Books.class.getName();
            Query<Books> query = session.createQuery(hql);
            resultlist = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return resultlist;
    }

    /**
     * This method used for get Books List  from Database by Author ID
     *
     * @param id Author Id
     * @return List Of Books
     */
    @SuppressWarnings("unchecked")
    public List<Books> getBooksByIdAuth(long id) {

        List<Books> resultlist = null;
        try {
            session = getSession();
            String hql = "from " + Books.class.getName() + " where author.id=:idq";
            Query<Books> query = session.createQuery(hql);
            query.setParameter("idq", id);
            resultlist = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return resultlist;
    }

    /**
     * This method used for get Books_Id List  from Database by Genres ID
     *
     * @param id Genres Id
     * @return List Of Books
     */
    @SuppressWarnings("unchecked")
    public List<BigInteger> getBooksGenId(long id) {
        List<BigInteger> resultlist = null;
        try {
            session = getSession();
            String sql = "select book_id from books_genres where genre_id=" + id;
            NativeQuery<BigInteger> nativeQuery = session.createNativeQuery(sql);
            resultlist = nativeQuery.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return resultlist;
    }

    /**
     * This method used for get Book Object  from Database by Book ID
     *
     * @param id Book Id
     * @return Books Object
     */
    @SuppressWarnings("unchecked")
    public Books getBooksById(long id) {
        Books books = null;
        try {
            session = getSession();
            books = session.get(Books.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return books;
    }

    /**
     * This method used for delete Book column from Database book table by Book name and year
     *
     * @param name Book name
     * @param year Book Publish Year
     * @return Boolean If deleted True or else false
     */
    @SuppressWarnings("unchecked")
    public boolean deleteBookByNameYr(String name, String year) {
        try {
            session = getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "from " + Books.class.getName() + " where book_name=:nm and year_of_publish=:yr";
            Query<Books> query = session.createQuery(hql);
            query.setParameter("nm", name).setParameter("yr", year);
            Books books = query.uniqueResult();
            if (books != null) {
                session.delete(books);
                transaction.commit();
                return true;
            }

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method used for get Book_Id  from Database book table  by Book_name
     *
     * @param name Book name
     * @return Books Object
     */
    @SuppressWarnings("unchecked")
    public Books getBookIdByName(String name) {
        Books books = null;
        try {
            session = getSession();
            String hql = "from " + Books.class.getName() + " where book_name=:nm";
            Query<Books> query = session.createQuery(hql);
            query.setParameter("nm", name);
            books = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return books;
    }

    /**
     * This method used for update Books Details.
     * It's Search for given book name in RequestBody.
     *
     * @param books Old Books object
     * @return Updated Books Object
     */
    public Books updateBook(Books books) {
        Books bookIdByName = getBookIdByName(books.getBook_name());
        if (bookIdByName != null) {
            session = getSession();
            Transaction transaction = session.beginTransaction();
            bookIdByName.setBook_name(books.getBook_name());
            bookIdByName.setGenres(books.getGenres());
            bookIdByName.setAuthor(books.getAuthor());
            bookIdByName.setPrice(books.getPrice());
            bookIdByName.setYear_of_publish(books.getYear_of_publish());
            session.update(bookIdByName);
            transaction.commit();
        }
        return bookIdByName;
    }

    /**
     * @param name Book Name
     * @return Book Object
     */
    public Books getBookByName(String name) {
        return getBookIdByName(name);
    }

    /**
     * This method gives Author Id By Book Id
     *
     * @param name Book name
     * @return List of Authors IDs
     */
    @SuppressWarnings("unchecked")
    public long getAuthorByBookId(String name) {
        long authorsId = 0;
        try {
            session = getSession();
            String hql = "select author.id from " + Books.class.getName() + " where book_name=:nm";
            Query<Long> query = session.createQuery(hql);
            query.setParameter("nm", name);
            authorsId = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return authorsId;
    }
}
