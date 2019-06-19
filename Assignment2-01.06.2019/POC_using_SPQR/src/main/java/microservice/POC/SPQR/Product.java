package microservice.POC.SPQR;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product")
@ToString
public class Product {

    @Id
    private String productId;
    private String productName;
    private String productModel;
    private Integer productCost;

    public Product() {
    }

    public Product(String productId, String productName, String productModel, Integer productCost) {
        this.productId = productId;
        this.productName = productName;
        this.productModel = productModel;
        this.productCost = productCost;
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

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public Integer getProductCost() {
        return productCost;
    }

    public void setProductCost(Integer productCost) {
        this.productCost = productCost;
    }
}
