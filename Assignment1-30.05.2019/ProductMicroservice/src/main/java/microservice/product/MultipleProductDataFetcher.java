package microservice.product;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MultipleProductDataFetcher implements DataFetcher<List<Product>> {

    @Autowired
    ProductRepository productRepository;

    public List<Product> get(DataFetchingEnvironment dataFetchingEnvironment) {
        Integer productCost = dataFetchingEnvironment.getArgument("productCost");
        if(productCost!=null)   return productRepository.findByProductCost(productCost);

        String productModel = dataFetchingEnvironment.getArgument("productModel");
        if(productModel!=null)  return productRepository.findByProductModel(productModel);

        return productRepository.findAll();
    }
}
