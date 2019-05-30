package microservice.customer;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer,String> {
    public List<Customer> findByAge(Integer age);
    public Customer findByCustomerId(String customerId);
    public Customer findByFirstName(String firstName);
    public Customer findByLastName(String lastName);
}
