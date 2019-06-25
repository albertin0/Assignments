package microservice.POC.SPQR;

import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import io.leangen.graphql.spqr.spring.autoconfigure.DefaultGlobalContext;
import microservice.POC.SPQR.exceptions.*;
import microservice.POC.SPQR.jwt.JwtTokenUtil;
import microservice.POC.SPQR.models.AuthenticationData;
import microservice.POC.SPQR.models.Product;
import microservice.POC.SPQR.models.User;
import microservice.POC.SPQR.repository.ProductRepository;
import microservice.POC.SPQR.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@GraphQLApi
public class GraphQLService {

    private static final Logger logger = LoggerFactory.getLogger(GraphQLQuery.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    LoggedInUserBean loggedInUserBean;

    @GraphQLQuery(name = "greeting")
    @PreAuthorize("hasRole('ADMIN')")
    public String getGreeting(String name, @GraphQLEnvironment ResolutionEnvironment env) throws UserNotLoggedInException, NotFoundException {
//        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
//        HttpServletRequest request = dgc.getServletRequest();
//        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
//            return "Hello " + name + "!";
//        }
//        throw new UserNotLoggedInException();
        if(loggedInUserBean.getLoggedInUser()!=null && loggedInUserBean.getLoggedInUser().getToken()!=null
        && loggedInUserBean.getLoggedInUser().getToken().size()>0) {
            String token = loggedInUserBean.getLoggedInUser().getToken().get(0);
            if (jwtTokenUtil.isTokenValid(token)) {
                return "Hello " + name + "!";
            }
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "allUsers")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<User> findAllUsers(@GraphQLEnvironment ResolutionEnvironment env)   throws UserNotLoggedInException, NotFoundException {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
            return userRepository.findAll();
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "allUsers")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<User> findAllUsers(Integer age, @GraphQLEnvironment ResolutionEnvironment env)  throws UserNotLoggedInException, NotFoundException {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
            return userRepository.findByAge(age);
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public User findById(String id, @GraphQLEnvironment ResolutionEnvironment env)  throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
            Optional<User> result = userRepository.findById(id);
            if(!result.isPresent()) {
                return null;
            }
            return result.get();
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "allUsers")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<User> findByFirstName(String firstName, @GraphQLEnvironment ResolutionEnvironment env)   throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
            return userRepository.findByFirstName(firstName);
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "allUsers")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<User> findByLastName(String lastName, @GraphQLEnvironment ResolutionEnvironment env)   throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
            return userRepository.findByLastName(lastName);
        }
        throw new UserNotLoggedInException();
    }

//    @GraphQLMutation(name = "createUser")
//    public String createUser(String firstName, String lastName, String userName, String password, Integer age, String role) {
//        User user = new User(firstName,lastName,userName,password,age,role);
//        userRepository.save(user);
//        return user.toString()+" saved to db.";
//    }

    @GraphQLMutation(name = "updateUser")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUser(String firstName, String lastName, String userName, String password, Integer age, String role, @GraphQLEnvironment ResolutionEnvironment env) throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
            User user = userRepository.findByUserName(userName);
            if(user==null)  return "No user with userName: "+userName;
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(password);
            user.setAge(age);
            user.addRole(role);
            userRepository.save(user);
            return user.toString() + " updated to db.";
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLMutation(name = "deleteUser")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(String id, @GraphQLEnvironment ResolutionEnvironment env)    throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
            Optional<User> result = userRepository.findById(id);
            if(!result.isPresent()) {
                return "No user with id = " + id + " present in db.";
            }
            User user = result.get();
            userRepository.delete(user);
            return user.toString() + " deleted from repository.";
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLMutation(name = "deleteAllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAllUsers(@GraphQLEnvironment ResolutionEnvironment env)  throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
            userRepository.deleteAll();
            return "every thing deleted User from Repository.";
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "product")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Product findByProductId(String productId, @GraphQLEnvironment ResolutionEnvironment env)   throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
            Optional<Product> result = productRepository.findById(productId);
            if(!result.isPresent()) {
                return null;
            }
            return result.get();
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "allProducts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Product> findAllProducts(@GraphQLEnvironment ResolutionEnvironment env)    throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
            return productRepository.findAll();
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "allProducts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Product> findAllProducts(String productName, @GraphQLEnvironment ResolutionEnvironment env) throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
            return productRepository.findByProductName(productName);
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "allProducts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Product> findByProductModel(String productModel, @GraphQLEnvironment ResolutionEnvironment env)   throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
            return productRepository.findByProductModel(productModel);
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "allProducts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Product> findByProductCost(Integer productCost, @GraphQLEnvironment ResolutionEnvironment env)   throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
            return productRepository.findByProductCost(productCost);
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLMutation(name = "createProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public String createProduct(String productId, String productName, String productModel, Integer productCost, @GraphQLEnvironment ResolutionEnvironment env) throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
            Product product = new Product(productId,productName,productModel,productCost);
            productRepository.save(product);
            return product.toString()+" saved to db.";
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLMutation(name = "updateProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateProduct(String productId, String productName, String productModel, Integer productCost, @GraphQLEnvironment ResolutionEnvironment env) throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
            Product product = new Product(productId,productName,productModel,productCost);
            productRepository.save(product);
            return product.toString() + " updated to db.";
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLMutation(name = "deleteProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteProduct(String productId, @GraphQLEnvironment ResolutionEnvironment env)    throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
            Optional<Product> result = productRepository.findById(productId);
            if(!result.isPresent())
                return "No Product with id = " + productId + " in db.";
            Product product = result.get();
            productRepository.delete(product);
            return product.toString() + " deleted from repository.";
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLMutation(name = "deleteAllProducts")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAllProducts(@GraphQLEnvironment ResolutionEnvironment env)  throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
            productRepository.deleteAll();
            return "every thing deleted from Product Repository.";
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLMutation
    public User createUser(String firstName, String lastName, String password, String userName,Integer age, String role) {
        if (userRepository.findByUserName(userName) != null) {
            logger.info("User already exist..." + userName);
            throw new UserAlreadyExistsException("User Already Exists");
        } else {
            User user = new User(firstName,lastName,userName,passwordEncoder.encode(password),age,role);
            userRepository.save(user);
            logger.info("User registered..." + userName);
            return user;
        }
    }

    @GraphQLMutation(name = "loginUser")
    public User loginUser(AuthenticationData authData, @GraphQLEnvironment ResolutionEnvironment env)
            throws UserNotFoundException, InvalidCredentialsException {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        User user = userRepository.findByUserName(authData.getUserName());
        if (user != null) {
            if (passwordEncoder.matches(authData.getPassword(), user.getPassword())) {
                @SuppressWarnings("unchecked")
                List<String> messages;
                messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
//                if(messages!=null)  logger.info("messages: " + messages.toString());
                if (messages == null || messages.size()==0) {
                    if(messages==null)
                        messages = new ArrayList<>();
                    //request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
                    String token = jwtTokenUtil.generateToken(user.getUserName());
                    messages.add(token);
                    request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
                    logger.info("User logged in with userName..." + authData.getUserName() + " and token " + token);
                    user.setToken(messages);
                    userRepository.save(user);
                    loggedInUserBean.setLoggedInUser(user);
                    return user;
                } else {
                    return user;
                }
            } else {
                logger.info("invalid credentials to login..." + authData.getUserName());
                throw new InvalidCredentialsException();
            }
        } else {
            logger.info("User not present to login..." + authData.getUserName());
            throw new UserNotFoundException();
        }
    }

    @GraphQLMutation
    public Boolean logout(@GraphQLEnvironment ResolutionEnvironment env) {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
//        jwtTokenUtil.invalidateToken(request.getHeader("Authorization"));
        logger.info("User logged out with userName..." + loggedInUserBean.getLoggedInUser().getUserName() + " and token " + loggedInUserBean.getLoggedInUser().getToken().toString());
        jwtTokenUtil.invalidateToken(loggedInUserBean.getLoggedInUser().getToken().get(0));
        request.getSession().invalidate();
        loggedInUserBean.setLoggedInUser(null);
        return true;
    }
}
