package microservice.POC.SPQR;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    //public microservice.POC.SPQR.User findById(String id);
    public User findByFirstName(String firstName);
    public User findByLastName(String lastName);
    public List<User> findByAge(Integer age);
}
