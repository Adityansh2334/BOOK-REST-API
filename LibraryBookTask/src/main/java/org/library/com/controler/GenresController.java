package org.library.com.controler;


import org.library.com.entity.Genres;
import org.library.com.services.GenresServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Aditya
 * mark this class as Controller for RESTAPI
 */
@RestController
public class GenresController {
    /**
     * autowire GenresServices class
     */
    @Autowired
    GenresServices genresServices;

    /**
     * creating post mapping for saving genres data into Database
     *
     * @param genres genres Object
     * @return String response
     */
    @ResponseBody
    @PostMapping("/savegenres")
    private String saveGenresDetails(@RequestBody Genres genres) {
        Genres genres1 = genresServices.getGenres(genres.getGenres_name());
        if(genres1!=null){
            if(genres.getGenres_desc()!=null){
                genres1.setGenres_desc(genres.getGenres_desc());
                genresServices.saveGenres(genres1);
            }
            return "Already Present in Database";
        }
        genresServices.saveGenres(genres);
        return "Saved GenresDetails" + genres.getGenres_name();
    }

    /**
     * creating get mapping for retriving genres data from database
     *
     * @return List of Genres
     */
    @ResponseBody
    @GetMapping("/genres")
    private List<Genres> getAllGenresDetails() {
        return genresServices.getAllGenres();
    }
}
