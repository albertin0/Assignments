package microservice.POC.SPQR;

import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import io.leangen.graphql.spqr.spring.autoconfigure.DefaultGlobalContext;
import microservice.POC.SPQR.exceptions.*;
import microservice.POC.SPQR.jwt.JwtTokenUtil;
import microservice.POC.SPQR.models.*;
import microservice.POC.SPQR.repository.ProductRepository;
//import microservice.POC.SPQR.repository.UserElasticRepository;
import microservice.POC.SPQR.repository.UserElasticRepositoryUsingTemplate;
import microservice.POC.SPQR.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    UserElasticRepositoryUsingTemplate userElasticRepositoryUsingTemplate;

//    @Autowired
//    UserElasticRepository userElasticRepository;

    @GraphQLQuery(name = "greeting")
    @PreAuthorize("hasRole('ADMIN')")
    public String getGreeting(String name, @GraphQLEnvironment ResolutionEnvironment env) throws UserNotLoggedInException, NotFoundException {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
//        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
//            return "Hello " + name + "!";
//        }
//        throw new UserNotLoggedInException();
//        if(loggedInUserBean.getLoggedInUser()!=null && loggedInUserBean.getLoggedInUser().getToken()!=null
//        && loggedInUserBean.getLoggedInUser().getToken().size()>0) {
//            String token = loggedInUserBean.getLoggedInUser().getToken().get(0);
//            if (jwtTokenUtil.isTokenValid(token)) {
//                return "Hello " + name + "!";
//            }
//        }
//        throw new UserNotLoggedInException();
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        if(jwtTokenUtil.isTokenValid(token))    {
            return "Hello " + name + "!";
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "allUsers")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<UserElastic> findAllUsers(@GraphQLEnvironment ResolutionEnvironment env)   throws UserNotLoggedInException, NotFoundException, Exception {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        List<String> roles = (List<String>)request.getSession().getAttribute("MY_USER_ROLES");
//        if (jwtTokenUtil.isTokenValid(request.getHeader("Authorization"))) {
        if (jwtTokenUtil.isTokenValid(token)) {
            List<UserElastic> toReturnList = new ArrayList<>();
//            userRepository.findAll().forEach(userElastic -> {
//                try {
//                    toReturnList.add((User) ObjectInstantiator.instantiateObject(userElastic,roles));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
            userElasticRepositoryUsingTemplate.findAll().forEach(userElastic -> {
                try {
                    toReturnList.add((UserElastic)ObjectInstantiator.instantiateObject(userElastic,roles));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return toReturnList;
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "allUsers")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<UserElastic> findAllUsers(Integer age, @GraphQLEnvironment ResolutionEnvironment env)  throws UserNotLoggedInException, NotFoundException, Exception {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        List<String> roles = (List<String>)request.getSession().getAttribute("MY_USER_ROLES");
        if (jwtTokenUtil.isTokenValid(token)) {
            List<UserElastic> toReturnList = new ArrayList<>();
            userElasticRepositoryUsingTemplate.findByAge(age).forEach(userElastic -> {
                try {
                    toReturnList.add((UserElastic)ObjectInstantiator.instantiateObject(userElastic,roles));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return toReturnList;
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserElastic findByUserName(String userName, @GraphQLEnvironment ResolutionEnvironment env)  throws UserNotLoggedInException, NotFoundException, Exception   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        List<String> roles = (List<String>)request.getSession().getAttribute("MY_USER_ROLES");
        if (jwtTokenUtil.isTokenValid(token)) {
            UserElastic result = userElasticRepositoryUsingTemplate.findByUserName(userName);
            if(result == null) {
                return null;
            }
            UserElastic toReturnResult = (UserElastic) ObjectInstantiator.instantiateObject(result,roles);
            return toReturnResult;
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "allUsers")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<UserElastic> findByFirstName(String firstName, @GraphQLEnvironment ResolutionEnvironment env)   throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        if (jwtTokenUtil.isTokenValid(token)) {
            return userElasticRepositoryUsingTemplate.findByFirstName(firstName);
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "allUsers")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<UserElastic> findByLastName(String lastName, @GraphQLEnvironment ResolutionEnvironment env)   throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");

        if (jwtTokenUtil.isTokenValid(token)) {
            return userElasticRepositoryUsingTemplate.findByLastName(lastName);
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
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        if (jwtTokenUtil.isTokenValid(token)) {
            User user = userRepository.findByUserName(userName);
//            UserElastic userElastic = userElasticRepository.findByUserName(userName);
            if(user==null)  return "No user with userName: "+userName;
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(password);
            user.setAge(age);
            user.addRole(role);
            userRepository.save(user);

//            userElastic.setFirstName(firstName);
//            userElastic.setLastName(lastName);
//            userElastic.setPassword(password);
//            userElastic.setAge(age);
//            userElastic.addRole(role);
//            userElasticRepository.save(userElastic);
            return user.toString() + " updated to both dbs(mongo and elastic).";
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLMutation(name = "deleteUser")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(String id, @GraphQLEnvironment ResolutionEnvironment env)    throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        if (jwtTokenUtil.isTokenValid(token)) {
            Optional<User> result = userRepository.findById(id);
//            Optional<UserElastic> resultElastic = userElasticRepository.findById(id);
            if(!result.isPresent()) {
                return "No user with id = " + id + " present in db.";
            }
            User user = result.get();
            userRepository.delete(user);

//            UserElastic userElastic = resultElastic.get();
//            userElasticRepository.delete(userElastic);
            return user.toString() + " deleted from repository.";
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLMutation(name = "deleteAllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAllUsers(@GraphQLEnvironment ResolutionEnvironment env)  throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        if (jwtTokenUtil.isTokenValid(token)) {
            userRepository.deleteAll();
//            userElasticRepository.deleteAll();
            return "every thing deleted from userRepository and userElasticRepository.";
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "product")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Product findByProductId(String productId, @GraphQLEnvironment ResolutionEnvironment env)   throws UserNotLoggedInException, NotFoundException, Exception   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        List<String> roles = (List<String>)request.getSession().getAttribute("MY_USER_ROLES");
        if (jwtTokenUtil.isTokenValid(token)) {
            Optional<Product> result = productRepository.findById(productId);
            if(!result.isPresent()) {
                return null;
            }
            Product toReturn = (Product)ObjectInstantiator.instantiateObject(result.get(),roles);;
            return toReturn;
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "allProducts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Product> findAllProducts(@GraphQLEnvironment ResolutionEnvironment env)    throws UserNotLoggedInException, NotFoundException, Exception   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        List<String> roles = (List<String>)request.getSession().getAttribute("MY_USER_ROLES");
        if (jwtTokenUtil.isTokenValid(token)) {
            List<Product> productsList = productRepository.findAll();
            List<Product> toReturnList = new ArrayList<>();
            for(Product product: productsList)  {
                toReturnList.add((Product)ObjectInstantiator.instantiateObject(product,roles));
            }
            return toReturnList;
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "allProducts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Product> findAllProducts(String productName, @GraphQLEnvironment ResolutionEnvironment env) throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        if (jwtTokenUtil.isTokenValid(token)) {
            return productRepository.findByProductName(productName);
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "allProducts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Product> findByProductModel(String productModel, @GraphQLEnvironment ResolutionEnvironment env)   throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        if (jwtTokenUtil.isTokenValid(token)) {
            return productRepository.findByProductModel(productModel);
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLQuery(name = "allProducts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Product> findByProductCost(Integer productCost, @GraphQLEnvironment ResolutionEnvironment env)   throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        if (jwtTokenUtil.isTokenValid(token)) {
            return productRepository.findByProductCost(productCost);
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLMutation(name = "createProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public String createProduct(String productId, String productName, String productModel, Integer productCost, @GraphQLEnvironment ResolutionEnvironment env) throws UserNotLoggedInException, NotFoundException   {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        if (jwtTokenUtil.isTokenValid(token)) {
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
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        if (jwtTokenUtil.isTokenValid(token)) {
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
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        if (jwtTokenUtil.isTokenValid(token)) {
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
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        if (jwtTokenUtil.isTokenValid(token)) {
            productRepository.deleteAll();
            return "every thing deleted from Product Repository.";
        }
        throw new UserNotLoggedInException();
    }

    @GraphQLMutation(name = "createCourse")
    public String createCourse(String courseName,Integer courseCost,String courseCode)   throws Exception    {
        CourseElastic courseElastic = new CourseElastic(courseName,courseCost,courseCode);
        userElasticRepositoryUsingTemplate.createCourse(courseElastic);
        return courseElastic.toString() + " added to db.";
    }

    @GraphQLQuery(name = "allCourses")
    public List<CourseElastic> findByCourseCost(Integer courseCost)    {
        return userElasticRepositoryUsingTemplate.findByCourseCost(courseCost);
    }

    @GraphQLQuery(name = "allCourses")
    public List<CourseElastic> findAllCourses()    {
        return userElasticRepositoryUsingTemplate.findAllCourses();
    }

    @GraphQLMutation(name = "createUser")
    public UserElastic createUser(String firstName, String lastName, String password, String userName,Integer age, String role) {
//        if (userElasticRepositoryUsingTemplate.findByUserName(userName) != null) {
//            logger.info("User already exist..." + userName);
//            throw new UserAlreadyExistsException("User Already Exists");
//        } else {
            UserElastic userElastic = new UserElastic(firstName,lastName,userName,passwordEncoder.encode(password),age,role);
            userElastic.setId(String.valueOf(new Date().getTime()));
            userElasticRepositoryUsingTemplate.createUser(userElastic);

//            UserElastic userElastic = new UserElastic(firstName,lastName,userName,passwordEncoder.encode(password),age,role);
//            userElastic.setId(String.valueOf(new Date().getTime()));
//            userElasticRepository.save(userElastic);
            logger.info("User registered..." + userName);
            return userElastic;
//        }
    }

    @GraphQLMutation(name = "loginUser")
    public User loginUser(AuthenticationData authData, @GraphQLEnvironment ResolutionEnvironment env)
            throws UserNotFoundException, InvalidCredentialsException, UserAlreadyLoggedInException {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
//        User user = userElasticRepository.findByUserName(authData.getUserName());
        User user = userRepository.findByUserName(authData.getUserName());
        if (user != null) {
            if (passwordEncoder.matches(authData.getPassword(), user.getPassword())) {
                if((String)request.getSession().getAttribute("MY_SESSION_TOKEN")==null) {
                    //                @SuppressWarnings("unchecked")
                    HashMap<String, String> idTokenMap = user.getToken();
                    if (idTokenMap == null) idTokenMap = new HashMap<>();
                    //                List<String> messages;
                    //                messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
                    //                if(messages!=null)  logger.info("messages: " + messages.toString());
                    //                if (messages == null || messages.size()==0) {
                    //                    if(messages==null)
                    //                        messages = new ArrayList<>();
                    //request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
                    String token = jwtTokenUtil.generateToken(user.getUserName(), request);
                    idTokenMap.put(request.getSession().getId(), token);
                    //                    request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
                    request.getSession().setAttribute("MY_SESSION_TOKEN", token);
                    request.getSession().setAttribute("MY_SESSION_USERNAME", user.getUserName());
                    request.getSession().setAttribute("MY_USER_ROLES", user.getRole());
                    logger.info("User logged in with userName..." + authData.getUserName() + " and token " + token);
                    user.setToken(idTokenMap);
                    userRepository.save(user);

//                    userElastic.setToken(idTokenMap);
//                    userElasticRepository.save(userElastic);
                    return user;
                }
                else    if(((String)request.getSession().getAttribute("MY_SESSION_USERNAME")).equals(user.getUserName())){
                    logger.info("User already LoggedIn... " + user.getUserName());
                    return user;
                }
                else    {
                    logger.info("User already LoggedIn... " + user.getUserName());
                    throw new UserAlreadyLoggedInException();
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
    public String logout(@GraphQLEnvironment ResolutionEnvironment env) {
        DefaultGlobalContext dgc = env.dataFetchingEnvironment.getContext();
        HttpServletRequest request = dgc.getServletRequest();
//        jwtTokenUtil.invalidateToken(request.getHeader("Authorization"));
        String token = (String)request.getSession().getAttribute("MY_SESSION_TOKEN");
        String userName = (String)request.getSession().getAttribute("MY_SESSION_USERNAME");
        jwtTokenUtil.invalidateToken(token);
        request.getSession().invalidate();
        logger.info("User logged out with userName..." + userName + " and token " + token);
//        loggedInUserBean.setLoggedInUser(null);
        return userName + " logged out successfully.";
    }
}
