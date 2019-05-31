package microservice.order.responseProduct;

import lombok.ToString;

@ToString
public class Product {

    private String productId;
    private String productName;
    private Integer productCost;
    private String productModel;

    public Product() {
    }

    public Product(String productId, String productName, Integer productCost, String productModel) {
        this.productId = productId;
        this.productName = productName;
        this.productCost = productCost;
        this.productModel = productModel;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductCost() {
        return productCost;
    }

    public void setProductCost(Integer productCost) {
        this.productCost = productCost;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }
}
