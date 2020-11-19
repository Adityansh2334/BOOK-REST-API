package org.library.com.services;

import org.library.com.dao.AuthorsDao;
import org.library.com.entity.Authors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aditya
 * <p>
 * Here Difining bussiness logics for Authors
 */
@Service
public class AuthorsServices {
    @Autowired
    AuthorsDao authorsDao;

    /**
     * saving all Authors Details into DB
     *
     * @param authors Authors Object
     */
    public void saveAuthors(Authors authors) {
        authorsDao.saveAuthorsDetails(authors);
    }

    /**
     * getting specific author Object by author
     * name and mail id.
     *
     * @param name Author name
     * @param mail Author mail
     * @return Author Object
     */
    public Authors getAuthor(String name, String mail) {
        return authorsDao.getAuthorPresent(name, mail);
    }

    /**
     * getting author Id by Author name
     *
     * @param name Author name
     * @return Author id
     */
    public long getIdbyName(String name) {
        return authorsDao.getAuthorIdByName(name);
    }

    /**
     * getting author mail id by author name
     *
     * @param name Author name
     * @return Author mail
     */
    public String getMailByName(String name) {
        return authorsDao.getAuthorMail(name);
    }

    /**
     * getting all Authors List
     *
     * @return List Of Authors
     */
    public List<Authors> getAllAuthors() {
        return authorsDao.getAllAuthorsDetails();
    }

    /**
     * For getting Author Object by Author name
     *
     * @param name String Author name
     * @return Author Object
     */
    public Authors getAuthorByName(String name) {
        return authorsDao.getAuthorByName(name);
    }

    /**
     * @param id Author Id
     * @return Authors Object
     */
    public Authors getAuthorById(long id) {
        return authorsDao.getAuthorById(id);
    }
}
