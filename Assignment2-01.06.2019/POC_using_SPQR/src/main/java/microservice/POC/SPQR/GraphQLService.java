package microservice.POC.SPQR;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@GraphQLApi
public class GraphQLService {

    @Autowired
    UserRepository userRepository;

    @GraphQLQuery(name = "allUsers")
    public List<User> findAllUsers()    {
        return userRepository.findAll();
    }

    @GraphQLQuery(name = "allUsers")
    public List<User> findAllUsers(Integer age) {
        return userRepository.findByAge(age);
    }

    @GraphQLQuery(name = "user")
    public User findByFirstName(String firstName)   {
        return userRepository.findByFirstName(firstName);
    }

    @GraphQLQuery(name = "user")
    public User findByLastName(String lastName)   {
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
        return "every thing deleted from Repository.";
    }
}
