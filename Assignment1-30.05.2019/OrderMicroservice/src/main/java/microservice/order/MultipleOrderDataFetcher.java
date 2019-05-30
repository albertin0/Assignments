package microservice.order;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MultipleOrderDataFetcher implements DataFetcher<List<Order>> {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<Order> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return orderRepository.findAll();
    }
}
