package microservice.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class SpringBootOrderMicroService {
    public static void main(String[] args)  {
        SpringApplication.run(SpringBootOrderMicroService.class, args);
    }
}
