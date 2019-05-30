package microservice.customer;

import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerController {

    @Autowired
    GraphQLService graphQLService;

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping(value = "/webservice2/create")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer)   {
        customerRepository.save(customer);
        return  new ResponseEntity<String>(customer.toString()+ " added to db.", HttpStatus.OK);
    }

    @PostMapping(value = "/webservice2/retrieve")
    public ResponseEntity<Object> retrieveCustomers(@RequestBody String query)    {
        ExecutionResult execute = graphQLService.getGraphQL().execute(query);
        return new ResponseEntity<>(execute, HttpStatus.OK);
    }

    @PutMapping(value = "/webservice2/update")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer)   {
        customerRepository.save(customer);
        return new ResponseEntity<String>(customer.toString() + " updated to db.", HttpStatus.OK);
    }

    @DeleteMapping(value = "/webservice2/delete/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId);
        if(customer!=null)   {
            customerRepository.delete(customer);
            return new ResponseEntity<String>(customer.toString() + " deleted from db.", HttpStatus.OK);
        }
        return new ResponseEntity<>(customerId + ", no customer with this Id in db.", HttpStatus.OK);
    }
}
