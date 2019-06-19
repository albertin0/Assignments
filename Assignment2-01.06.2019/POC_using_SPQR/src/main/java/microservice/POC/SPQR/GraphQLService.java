package microservice.POC.SPQR;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@GraphQLApi
public class GraphQLService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @GraphQLQuery(name = "allUsers")
    public List<User> findAllUsers()    {
        return userRepository.findAll();
    }

    @GraphQLQuery(name = "allUsers")
    public List<User> findAllUsers(Integer age) {
        return userRepository.findByAge(age);
    }

    @GraphQLQuery(name = "user")
    public User findById(String id)   {
        return userRepository.findById(id).get();
    }

    @GraphQLQuery(name = "allUsers")
    public List<User> findByFirstName(String firstName)   {
        return userRepository.findByFirstName(firstName);
    }

    @GraphQLQuery(name = "allUsers")
    public List<User> findByLastName(String lastName)   {
        return userRepository.findByLastName(lastName);
    }

    @GraphQLMutation(name = "createUser")
    public String createUser(String id, String firstName, String lastName, Integer age) {
        User user = new User(id,firstName,lastName,age);
        userRepository.save(user);
        return user.toString()+" saved to db.";
    }

    @GraphQLMutation(name = "updateUser")
    public String updateUser(String id, String firstName, String lastName, Integer age) {
        User user = new User(id,firstName,lastName,age);
        userRepository.save(user);
        return user.toString() + " updated to db.";
    }

    @GraphQLMutation(name = "deleteUser")
    public String deleteUser(String id)    {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
        return user.toString() + " deleted from repository.";
    }

    @GraphQLMutation(name = "deleteAllUsers")
    public String deleteAllUsers()  {
        userRepository.deleteAll();
        return "every thing deleted User from Repository.";
    }

    @GraphQLQuery(name = "product")
    public Product findByProductId(String productId)   {
        return productRepository.findById(productId).get();
    }

    @GraphQLQuery(name = "allProducts")
    public List<Product> findAllProducts()    {
        return productRepository.findAll();
    }

    @GraphQLQuery(name = "allProducts")
    public List<Product> findAllProducts(String productName) {
        return productRepository.findByProductName(productName);
    }

    @GraphQLQuery(name = "allProducts")
    public List<Product> findByProductModel(String productModel)   {
        return productRepository.findByProductModel(productModel);
    }

    @GraphQLQuery(name = "allProducts")
    public List<Product> findByProductCost(Integer productCost)   {
        return productRepository.findByProductCost(productCost);
    }

    @GraphQLMutation(name = "createProduct")
    public String createProduct(String productId, String productName, String productModel, Integer productCost) {
        Product product = new Product(productId,productName,productModel,productCost);
        productRepository.save(product);
        return product.toString()+" saved to db.";
    }

    @GraphQLMutation(name = "updateProduct")
    public String updateProduct(String productId, String productName, String productModel, Integer productCost) {
        Product product = new Product(productId,productName,productModel,productCost);
        productRepository.save(product);
        return product.toString() + " updated to db.";
    }

    @GraphQLMutation(name = "deleteUser")
    public String deleteProduct(String productId)    {
        Product product = findByProductId(productId);
        productRepository.delete(product);
        return product.toString() + " deleted from repository.";
    }

    @GraphQLMutation(name = "deleteAllProducts")
    public String deleteAllProducts()  {
        productRepository.deleteAll();
        return "every thing deleted from Product Repository.";
    }
}
