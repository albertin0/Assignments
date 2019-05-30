package microservice.order;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "order")
public class Order {
    @Id
    private String orderId;
    private String productId,customerId;
}
