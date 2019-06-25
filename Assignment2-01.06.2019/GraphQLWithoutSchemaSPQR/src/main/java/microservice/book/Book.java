package microservice.book;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Document(collection = "book2")
@ToString
public class Book {

    @Id
    private String id;
    private String title;
    private String isbn;
    private Integer pageCount;
    private Author author;
    private HashMap<String,String> idValMap;

    public Book() {
    }

    public Book(String id, String title, String isbn, Integer pageCount, Author author, HashMap<String,String> idValMap) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.author = author;
        this.idValMap = idValMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public HashMap<String, String> getIdValMap() {
        return idValMap;
    }

    public void setIdValMap(HashMap<String, String> idValMap) {
        this.idValMap = idValMap;
    }
}
