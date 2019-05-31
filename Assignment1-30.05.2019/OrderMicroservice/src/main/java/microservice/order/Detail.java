package microservice.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import microservice.order.responseCustomer.Customer;
import microservice.order.responseProduct.Product;

@Setter
@Getter
public class Detail {
    private Order order;
    private Customer customer;
    private Product product;

    public Detail(Order order, Customer customer, Product product) {
        this.order = order;
        this.customer = customer;
        this.product = product;
    }

    public Detail() {
    }
}
