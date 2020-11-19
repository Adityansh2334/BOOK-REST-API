package org.library.com.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Aditya
 * Entity POJO class for creating books Table in database
 * ORM
 */
@Entity
@Table(name = "books")
public class Books implements Serializable {
    @Id
    @GenericGenerator(name = "gen_id", strategy = "increment")
    @GeneratedValue(generator = "gen_id")
    @Column(name = "id")
    private long id;
    @Column(name = "book_name", unique = true)
    private String book_name;
    @Column(name = "year_of_publish")
    private String year_of_publish;
    @Column(name = "price")
    private Double price;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "books_genres",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private List<Genres> genres;
    @ManyToOne(cascade = CascadeType.ALL)
    private Authors author;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getYear_of_publish() {
        return year_of_publish;
    }

    public void setYear_of_publish(String year_of_publish) {
        this.year_of_publish = year_of_publish;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public Authors getAuthor() {
        return author;
    }

    public void setAuthor(Authors author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void addGenres(Genres genres) {
        this.genres.add(genres);
    }

}
