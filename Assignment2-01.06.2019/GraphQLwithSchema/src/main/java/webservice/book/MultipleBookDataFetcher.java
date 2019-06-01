package webservice.book;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MultipleBookDataFetcher implements DataFetcher<List<Book>> {

    @Autowired
    BookRepository bookRepository;

    public List<Book> get(DataFetchingEnvironment dataFetchingEnvironment)  {
        Integer pageCount = dataFetchingEnvironment.getArgument("pageCount");
        if(pageCount!=null) return bookRepository.findByPageCount(pageCount);
        return bookRepository.findAll();
    }
}
