package microservice.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
public class SpringBootCustomerMicroService {
    public static void main(String[] args)  {
        SpringApplication.run(SpringBootCustomerMicroService.class, args);
    }
}
