package microservice.customer;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "customer")
public class Customer {
    @Id
    private String customerId;
    private String firstName,lastName;
    private Integer age;
}
