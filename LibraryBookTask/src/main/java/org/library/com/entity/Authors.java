package org.library.com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Aditya
 * Entity POJO class for creating authors Table in database
 * ORM
 */
@Entity
@Table(name = "authors")
public class Authors implements Serializable {
    @Id
    @GenericGenerator(name = "gen_id", strategy = "increment")
    @GeneratedValue(generator = "gen_id")
    @Column(name = "id")
    private long id;
    @Column(name = "author_name")
    private String author_name;
    @Column(name = "author_mail", unique = true)
    private String author_mail;
    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Books> booksList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_mail() {
        return author_mail;
    }

    public void setAuthor_mail(String author_mail) {
        this.author_mail = author_mail;
    }

    public List<Books> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<Books> booksList) {
        this.booksList = booksList;
    }
}
