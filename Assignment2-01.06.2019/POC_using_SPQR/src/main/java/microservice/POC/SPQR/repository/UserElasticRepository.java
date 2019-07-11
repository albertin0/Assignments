//package microservice.POC.SPQR.repository;
//
//import microservice.POC.SPQR.models.User;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//
//import java.util.List;
//
//public interface UserElasticRepository extends ElasticsearchRepository<User, String> {
//    public List<User> findByFirstName(String firstName);
//    public List<User> findByLastName(String lastName);
//    public List<User> findByAge(Integer age);
//    public User findByUserName(String userName);
//}
