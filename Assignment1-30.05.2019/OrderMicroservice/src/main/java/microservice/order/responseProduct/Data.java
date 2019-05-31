package microservice.order.responseProduct;

public class Data {
    Product product;

    public Data() {
    }

    public Data(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
