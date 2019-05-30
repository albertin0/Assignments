package microservice.order;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDataFetcher implements DataFetcher<Order> {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order get(DataFetchingEnvironment dataFetchingEnvironment) {
        return orderRepository.findByOrderId(dataFetchingEnvironment.getArgument("orderId"));
    }
}
