package microservice.customer;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MultipleCustomerDataFetcher implements DataFetcher<List<Customer>> {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> get(DataFetchingEnvironment dataFetchingEnvironment) {
        Integer age = dataFetchingEnvironment.getArgument("age");
        if(age!=null)   return customerRepository.findByAge(age);

        return customerRepository.findAll();
    }
}
