package microservice.order.responseCustomer;

public class Data {
    Customer customer;

    public Data() {
    }

    public Data(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

