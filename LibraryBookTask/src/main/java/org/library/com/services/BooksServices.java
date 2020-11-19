package org.library.com.services;

import org.library.com.dao.BooksDao;
import org.library.com.entity.Authors;
import org.library.com.entity.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Aditya
 * <p>
 * Here Difining bussiness logics for Books
 */
@Service
public class BooksServices {
    @Autowired
    BooksDao booksDao;

    @Autowired
    AuthorsServices authorsServices;

    /**
     * saving books into Db
     *
     * @param books Books Object
     */
    public void saveBooks(Books books) {
        booksDao.saveBooksDetails(books);
    }

    /**
     * getting List of All Books present in DB
     *
     * @return List of Books
     */
    public List<Books> getBooks() {
        return booksDao.getBooksALl();
    }

    /**
     * getting List of Books by Authors_Id
     *
     * @param id Author Id
     * @return List Of Books
     */
    public List<Books> getBooksByAuthId(long id) {
        return booksDao.getBooksByIdAuth(id);
    }

    /**
     * getting List of Books data by Generes_Id
     *
     * @param id Genres Id
     * @return List of Books
     */
    public List<Books> getBooksByGenId(long id) {
        List<BigInteger> booksGenId = booksDao.getBooksGenId(id);
        List<Books> listBooks = new LinkedList<Books>();
        for (BigInteger next : booksGenId) {
            listBooks.add(booksDao.getBooksById(next.longValue()));
        }
        return listBooks;
    }

    /**
     * Using this method can delete specific book by giving
     * book name and year.
     *
     * @param name Book name
     * @param year Book publish year
     * @return boolean true if deleted else false
     */
    public boolean deleteBookByNameYr(String name, String year) {
        return booksDao.deleteBookByNameYr(name, year);
    }

    /**
     * for updating Books Details By Specific book name.
     * If book name not found in the book table the
     * sends 404 error.
     *
     * @param books old Books Object
     * @return updated Books Object
     */
    public Books updateBookDetails(Books books) {
        return booksDao.updateBook(books);
    }

    /**
     * This method gives Books Object by Book Name
     *
     * @param name String Book name
     * @return Book Object
     */
    public Books getBooksByName(String name) {
        return booksDao.getBookByName(name);
    }

    /**
     * This method gives Author Id By Book Id
     *
     * @param name Book name(String)
     * @return List of Authors
     */
    public Authors getAuthorsByBookIdName(String name) {
        long authorByBookId = booksDao.getAuthorByBookId(name);
        return authorsServices.getAuthorById(authorByBookId);
    }
}
