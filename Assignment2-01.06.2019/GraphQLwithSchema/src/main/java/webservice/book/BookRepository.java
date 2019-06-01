package webservice.book;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, ID> {
    public List<Book> findByPageCount(Integer pageCount);
    public Book findById(String id);
}
