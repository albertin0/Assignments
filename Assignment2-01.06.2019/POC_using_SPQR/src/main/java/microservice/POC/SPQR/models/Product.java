package microservice.POC.SPQR.models;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product")
@ToString
public class Product extends DataObject {


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

    public String getProductName() {
        return productName;
    }

    public String getProductModel() {
        return productModel;
    }

    public Integer getProductCost() {
        return productCost;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public void setProductCost(Integer productCost) {
        this.productCost = productCost;
    }
}
