package microservice.product;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDataFetcher implements DataFetcher<Product> {

    @Autowired
    ProductRepository productRepository;

    public Product get(DataFetchingEnvironment dataFetchingEnvironment) {
        String productId =dataFetchingEnvironment.getArgument("productId");
        if(productId!=null) return productRepository.findByProductId(productId);

        String productName = dataFetchingEnvironment.getArgument("productName");
        return productRepository.findByProductName(productName);
    }
}
