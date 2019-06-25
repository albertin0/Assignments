package microservice.book;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@GraphQLApi
public class GraphQLService {

    @Autowired
    BookRepository bookRepository;

    @GraphQLQuery(name = "allBooks")
    public List<Book> findAllBooks()    {
        return bookRepository.findAll();
    }

    @GraphQLQuery(name = "allBooks")
    public List<Book> findByPageCount(Integer pageCount)    {
        return bookRepository.findByPageCount(pageCount);
    }

    @GraphQLQuery(name = "book")
    public Book findByTitle(String title)   {
        return bookRepository.findByTitle(title);
    }

    @GraphQLQuery(name = "book")
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @GraphQLQuery(name = "book")
    public Book findById(String id) {
        return bookRepository.findById(id).get();
    }

    @GraphQLQuery(name = "book")
    public String findByIdValMap(String idOfMap) {
        return bookRepository.findByIdValMap(idOfMap);
    }

    @GraphQLMutation(name = "createBook")
    public String createBook(String id, String title, String isbn, Integer pageCount, Author author, HashMap<String, String> idValMap)    {
        Book book = new Book(id,title,isbn,pageCount,author,idValMap);
        bookRepository.save(book);
        return book.toString() + " saved to Repository.";
    }

    @GraphQLMutation(name = "updateBook")
    public Book updateBook(String id, String title, String isbn, Integer pageCount, Author author, HashMap<String, String> idValMap) {
        Book book = new Book(id, title, isbn, pageCount, author, idValMap);
        return bookRepository.save(book);
    }

    @GraphQLMutation(name = "deleteBook")
    public String deleteBook(String title)    {
        Book book = bookRepository.findByTitle(title);
        bookRepository.delete(book);
        return book.toString() + " deleted from repository.";
    }

    @GraphQLMutation(name = "deleteAllbooks")
    public String deleteAllBooks()  {
        bookRepository.deleteAll();
        return "every thing deleted from Repository.";
    }
}
