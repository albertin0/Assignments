package microservice.POC.SPQR.repository;

import microservice.POC.SPQR.models.CourseElastic;
import microservice.POC.SPQR.models.UserElastic;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserElasticRepositoryUsingTemplate {
    public List<UserElastic> findAll();
    public List<UserElastic> findByFirstName(String firstName);
    public List<UserElastic> findByLastName(String lastName);
    public List<UserElastic> findByAge(Integer age);
    public UserElastic findByUserName(String userName);

    public UserElastic createUser(UserElastic userElastic);
    public UserElastic updateUser(UserElastic userElastic);

    public void deleteUser(UserElastic userElastic);

    public CourseElastic createCourse(CourseElastic courseElastic);

    public List<CourseElastic> findByCourseCost(Integer cost);
    public List<CourseElastic> findAllCourses();
}