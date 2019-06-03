package microservice.book;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    //public Book findById(String id);
    public Book findByTitle(String title);
    public Book findByIsbn(String isbn);
    public List<Book> findByPageCount(Integer pageCount);
}
