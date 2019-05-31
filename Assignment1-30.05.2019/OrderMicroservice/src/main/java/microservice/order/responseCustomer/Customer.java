package microservice.order.responseCustomer;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
    private String customerId;
    private String firstName,lastName;
    private Integer age;
}
