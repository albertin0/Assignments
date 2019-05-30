package microservice.product;

import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    GraphQLService graphQLService;

    @Autowired
    ProductRepository productRepository;

    @PostMapping(value = "/webservice1/create")
    public ResponseEntity<String> createProduct(@RequestBody Product product)   {
        productRepository.save(product);

        return  new ResponseEntity<String>(product.toString()+ " added to db.", HttpStatus.OK);
    }

    @PostMapping(value = "/webservice1/retrieve")
    public ResponseEntity<Object> retrieveProducts(@RequestBody String query)    {
        ExecutionResult execute = graphQLService.getGraphQL().execute(query);

        return new ResponseEntity<>(execute, HttpStatus.OK);
    }

    @PutMapping(value = "/webservice1/update")
    public ResponseEntity<String> updateProduct(@RequestBody Product product)   {
        productRepository.save(product);
        return new ResponseEntity<String>(product.toString() + " updated to db.", HttpStatus.OK);
    }

    @DeleteMapping(value = "/webservice1/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId) {
        Product product = productRepository.findByProductId(productId);
        if(product!=null)   {
            productRepository.delete(product);
            return new ResponseEntity<String>(product.toString() + " deleted from db.", HttpStatus.OK);
        }
        return new ResponseEntity<>(productId + ", no product with this Id in db.", HttpStatus.OK);
    }
}
