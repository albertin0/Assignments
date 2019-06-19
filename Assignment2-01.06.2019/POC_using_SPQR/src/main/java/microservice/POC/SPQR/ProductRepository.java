package microservice.POC.SPQR;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product,String> {
    public List<Product> findByProductName(String productName);
    public List<Product> findByProductModel(String productModel);
    public List<Product> findByProductCost(Integer productCost);
}
