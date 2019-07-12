//package microservice.POC.SPQR.repository;
//
//import microservice.POC.SPQR.models.UserElastic;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface UserElasticRepository extends ElasticsearchRepository<UserElastic, String> {
//    public List<UserElastic> findByFirstName(String firstName);
//    public List<UserElastic> findByLastName(String lastName);
//    public List<UserElastic> findByAge(Integer age);
//    public UserElastic findByUserName(String userName);
//}
