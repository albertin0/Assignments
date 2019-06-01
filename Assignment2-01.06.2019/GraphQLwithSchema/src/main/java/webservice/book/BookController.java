package webservice.book;

import com.sun.xml.internal.bind.v2.model.core.ID;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    @Autowired
    GraphQLService graphQLService;

    @Autowired
    BookRepository bookRepository;

    @PostMapping(value = "/webservice/create")
    public ResponseEntity<String> createBook(@RequestBody Book book)    {
        bookRepository.save(book);
        return new ResponseEntity<String>(book.toString() + " added to db.", HttpStatus.OK);
    }

    @PostMapping(value = "/webservice/retrieve")
    public ResponseEntity<Object> retrieveBooks(@RequestBody String query)    {
        ExecutionResult execute = graphQLService.getGraphQL().execute(query);
        return new ResponseEntity<>(execute, HttpStatus.OK);
    }

    @PutMapping(value = "/webservice/update")
    public ResponseEntity<String> updateBook(@RequestBody Book book)   {
        bookRepository.save(book);
        return new ResponseEntity<String>(book.toString() + " updated to db.", HttpStatus.OK);
    }

    @DeleteMapping(value = "/webservice2/delete/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable ID bookId) {
        Book book = bookRepository.findById(bookId).get();
        if(book!=null)   {
            bookRepository.delete(book);
            return new ResponseEntity<String>(book.toString() + " deleted from db.", HttpStatus.OK);
        }
        return new ResponseEntity<>(bookId + ", no book with this Id in db.", HttpStatus.OK);
    }
}
