package microservice.POC.SPQR;

import microservice.POC.SPQR.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    //public microservice.POC.SPQR.models.User findById(String id);
    public List<User> findByFirstName(String firstName);
    public List<User> findByLastName(String lastName);
    public List<User> findByAge(Integer age);
}
