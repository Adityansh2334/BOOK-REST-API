package org.library.com.controler;

import org.library.com.entity.Authors;
import org.library.com.services.AuthorsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Aditya
 * mark this class as Controller for RESTAPI
 */
@RestController
public class AuthorsController {
    /**
     * autowire AuthorsServices class
     */
    @Autowired
    AuthorsServices authorsServices;

    /**
     * creating post mapping for saving authors data into Database
     *
     * @param authors authors Details
     * @return String response
     */
    @ResponseBody
    @PostMapping("/saveauthors")
    private String saveauthorsDetails(@RequestBody Authors authors) {
        if (authorsServices.getAuthor(authors.getAuthor_name(), authors.getAuthor_mail()) != null) {
            return "Already Saved in Database";
        }
        authorsServices.saveAuthors(authors);
        return "Authors Details Saved " + authors.getAuthor_name();
    }

    /**
     * creating get mapping for retriving authors data from database
     *
     * @return list of Authors
     */
    @ResponseBody
    @GetMapping("/authors")
    private List<Authors> getAllAuthorsDetails() {
        return authorsServices.getAllAuthors();
    }

}
