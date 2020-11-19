package org.library.com.services;

import org.library.com.dao.GenresDao;
import org.library.com.entity.Genres;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aditya
 * <p>
 * Here Difining bussiness logics for Genres
 */
@Service
public class GenresServices {
    @Autowired
    GenresDao genresDao;

    /**
     * saving Generes Object/Details into DB
     *
     * @param genres Genres Object
     */
    public void saveGenres(Genres genres) {
        genresDao.saveGenresDetails(genres);
    }

    /**
     * getting Genres Object by specific genres name
     * for future use
     *
     * @param genName Genres name
     * @return Genres Object
     */
    public Genres getGenres(String genName) {
        return genresDao.getGenresByName(genName);
    }

    /**
     * for getting Generes Object by genres name and
     * genres description.
     *
     * @param genName     Genres name
     * @param genres_desc Genres Description
     * @return Genres Object
     */
    public Genres getGenres(String genName, String genres_desc) {
        return genresDao.getGenresByName(genName, genres_desc);
    }

    /**
     * for getting genres Id by genres name
     *
     * @param name Genres Name
     * @return Genres Id
     */
    public long getGenIdByName(String name) {
        return genresDao.getGenIdByName(name);
    }

    /**
     * for getting all genres details List
     *
     * @return List of Genres
     */
    public List<Genres> getAllGenres() {
        return genresDao.getAllGenres();
    }
}
