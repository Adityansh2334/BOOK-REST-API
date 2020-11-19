package org.library.com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;


/**
 * @author Aditya
 * Entity POJO class for creating genres Table in database
 * ORM
 */
@Entity
@Table(name = "genres")
public class Genres implements Serializable {

    @Id
    @GenericGenerator(name = "gen_id", strategy = "increment")
    @GeneratedValue(generator = "gen_id")
    @Column(name = "id")
    private long id;
    @Column(name = "genres_name", unique = true)
    private String genres_name;
    @Column(name = "genres_desc")
    private String genres_desc;

    @JsonIgnore
    @ManyToMany(mappedBy = "genres", cascade = CascadeType.ALL)
    private List<Books> booksList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGenres_name() {
        return genres_name;
    }

    public void setGenres_name(String genres_name) {
        this.genres_name = genres_name;
    }

    public String getGenres_desc() {
        return genres_desc;
    }

    public void setGenres_desc(String genres_desc) {
        this.genres_desc = genres_desc;
    }

    public void setBooksList(List<Books> booksList) {
        this.booksList = booksList;
    }

    public List<Books> getBooksList() {
        return booksList;
    }
}
