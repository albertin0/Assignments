package microservice.product;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    public Product findByProductName(String productName);
    public List<Product> findByProductCost(Integer productCost);
    public List<Product> findByProductModel(String productModel);
    public Product findByProductId(String productId);
}
