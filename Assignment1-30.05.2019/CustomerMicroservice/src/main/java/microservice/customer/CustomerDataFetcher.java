package microservice.customer;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataFetcher implements DataFetcher<Customer> {

    @Autowired
    CustomerRepository customerRepository;

    public Customer get(DataFetchingEnvironment dataFetchingEnvironment)    {
        String customerId = dataFetchingEnvironment.getArgument("customerId");
        if(customerId!=null)    return customerRepository.findByCustomerId(customerId);

        String firstName = dataFetchingEnvironment.getArgument("firstName");
        if(firstName!=null)     return customerRepository.findByFirstName(firstName);

        String lastName = dataFetchingEnvironment.getArgument("lastName");
        return customerRepository.findByLastName(lastName);
    }
}
