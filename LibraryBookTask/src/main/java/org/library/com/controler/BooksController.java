package org.library.com.controler;

import org.library.com.entity.Authors;
import org.library.com.entity.Books;
import org.library.com.entity.Genres;
import org.library.com.services.AuthorsServices;
import org.library.com.services.BooksServices;
import org.library.com.services.GenresServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Aditya
 * mark this class as Controller for RESTAPI
 */
@RestController
public class BooksController {

    /**
     * autowire the BooksService class
     */
    @Autowired
    BooksServices booksServices;

    /**
     * autowire the AuthorsServices class
     */

    @Autowired
    AuthorsServices authorsServices;

    /**
     * autowire the GenresServices class
     */

    @Autowired
    GenresServices genresServices;

    /**
     * creating post mapping that post the book detail in the database
     *
     * @param books books details
     * @return String response
     */
    @ResponseBody
    @PostMapping("/savebooks")
    private String saveBooks(@RequestBody Books books) {
        if (booksServices.getBooksByName(books.getBook_name()) != null) {
            return "Already saved in Database";
        }
        List<Genres> genres = books.getGenres();
        Authors authorCheck = books.getAuthor();
        List<Genres> loadGen = new LinkedList<Genres>();
        if (authorCheck != null && !genres.isEmpty()) {
            Authors authGet = authorsServices.getAuthorByName(books.getAuthor().getAuthor_name());
            if (authGet != null) books.setAuthor(authGet);
            for (Genres next : genres) {
                String genres_name = next.getGenres_name();
                Genres genres1 = genresServices.getGenres(genres_name);
                if (next.getGenres_desc() != null) {
                    if (genres1 != null) genres1.setGenres_desc(next.getGenres_desc());
                }
                loadGen.add(Objects.requireNonNullElse(genres1, next));
            }
            books.setGenres(loadGen);
            if (authGet == null) books.setAuthor(authorCheck);
            booksServices.saveBooks(books);
            return "Book Saved " + books.getBook_name();
        } else {
            return " Generes Details Not Provided/ Authors Details not Provided";
        }
    }

    /**
     * creating a get mapping that retrieves all the books detail from the database
     *
     * @return list of Books
     */
    @ResponseBody
    @GetMapping("/books")
    private List<Books> getAllBooksDetails() {
        return booksServices.getBooks();
    }

    /**
     * creating a get mapping that retrieves all the books detail from the database written by specific author name
     *
     * @param name                String author name
     * @param httpServletResponse http servlet response
     * @return List of Books
     * @throws IOException it throws IOException
     */
    @ResponseBody
    @GetMapping("/books/author/{authorname}")
    private List<Books> getBooksByAuthorsName(@PathVariable("authorname") String name,
                                              HttpServletResponse httpServletResponse) throws IOException {
        List<Books> booksByAuthId = null;
        try {
            booksByAuthId = booksServices.getBooksByAuthId(authorsServices.getIdbyName(name));
            if (booksByAuthId == null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            httpServletResponse.sendError(404);
        }
        return booksByAuthId;
    }

    /**
     * creating a get mapping that retrieves all the books detail from the database by specific genres name
     *
     * @param name                genres name
     * @param httpServletResponse http servlet response send
     * @return List Of Books
     * @throws IOException it throws IOException
     */
    @ResponseBody
    @GetMapping("/books/genres/{genresname}")
    private List<Books> getBooksByGenresName(@PathVariable("genresname") String name,
                                             HttpServletResponse httpServletResponse) throws IOException {
        List<Books> booksByGenId = null;
        try {
            booksByGenId = booksServices.getBooksByGenId(genresServices.getGenIdByName(name));
            if (booksByGenId == null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            httpServletResponse.sendError(404);
        }
        return booksByGenId;
    }

    /**
     * creating a delete mapping that deletes a specified book by giving book name and year
     *
     * @param name                book name
     * @param year                publish Year of Book
     * @param httpServletResponse http servlet response send
     * @return String Response
     * @throws IOException it throws IOException
     */
    @ResponseBody
    @DeleteMapping("/books/delete/{bookname}/{bookyear}")
    private String deleteBookByName(@PathVariable("bookname") String name,
                                    @PathVariable("bookyear") String year,
                                    HttpServletResponse httpServletResponse) throws IOException {
        if (booksServices.deleteBookByNameYr(name, year)) {
            return "Successfully Deleted .Check Agian By> /books";
        } else httpServletResponse.sendError(404);
        return "[Nothing Found]";
    }


    /**
     * creating put mapping that updates the book detail
     * if given book name not present in the book table it sends error 404.
     *
     * @param books               books details
     * @param httpServletResponse http servlet response send
     * @return Books Object
     * @throws IOException it throws IOException
     */
    @ResponseBody
    @PutMapping("/books/update")
    private Books updateBooks(@RequestBody Books books,
                              HttpServletResponse httpServletResponse) throws IOException {
        List<Genres> genres = books.getGenres();
        List<Genres> loadGen = new LinkedList<Genres>();
        for (Genres next : genres) {
            Genres genres1 = genresServices.getGenres(next.getGenres_name());
            if (next.getGenres_desc() != null) if (genres1 != null) genres1.setGenres_desc(next.getGenres_desc());
            loadGen.add(Objects.requireNonNullElse(genres1, next));
        }
        books.setGenres(loadGen);
        Authors author = booksServices.getAuthorsByBookIdName(books.getBook_name());
        if (author != null) {
            if (!author.getAuthor_mail().equalsIgnoreCase(books.getAuthor().getAuthor_mail()) ||
                    !author.getAuthor_name().equalsIgnoreCase(books.getAuthor().getAuthor_name())) {
                author.setAuthor_mail(books.getAuthor().getAuthor_mail());
                author.setAuthor_name(books.getAuthor().getAuthor_name());
            }
            books.setAuthor(author);
        } else httpServletResponse.sendError(404, "Please check Author Details. It's Not in Our Database");
        Books books1 = booksServices.updateBookDetails(books);
        if (books1 != null) return books1;
        else httpServletResponse.sendError(404);
        return null;
    }


}
