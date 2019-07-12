package microservice.POC.SPQR.repository;

import microservice.POC.SPQR.models.CourseElastic;
import microservice.POC.SPQR.models.UserElastic;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@Repository
public class UserElasticRepositoryUsingTemplateImp implements UserElasticRepositoryUsingTemplate {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${elasticsearch.index.name}")
    private String indexName;

    @Value("${elasticsearch.user.type}")
    private String userTypeName;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Override
    public List<UserElastic> findAll() {
        SearchQuery getAllQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery()).build();
        return esTemplate.queryForList(getAllQuery, UserElastic.class);
    }

    @Override
    public List<UserElastic> findByFirstName(String firstName) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.matchQuery("firstName", firstName)).build();
        return esTemplate.queryForList(searchQuery, UserElastic.class);
    }

    @Override
    public List<UserElastic> findByLastName(String lastName) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.matchQuery("lastName", lastName)).build();
        return esTemplate.queryForList(searchQuery, UserElastic.class);
    }

    @Override
    public List<UserElastic> findByAge(Integer age) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.matchQuery("age", age)).build();
        return esTemplate.queryForList(searchQuery, UserElastic.class);
    }

    @Override
    public UserElastic findByUserName(String userName) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.matchQuery("userName", userName)).build();
        return esTemplate.queryForList(searchQuery, UserElastic.class).get(0);
    }

    @Override
    public UserElastic createUser(UserElastic user) {
        IndexQuery userQuery = new IndexQuery();
        userQuery.setIndexName(indexName);
        userQuery.setType(userTypeName);
        userQuery.setObject(user);

        logger.info("UserElastic indexed: {}", esTemplate.index(userQuery));
        esTemplate.refresh(indexName);

        return user;
    }

    @Override
    public UserElastic updateUser(UserElastic user) {
        IndexQuery userQuery = new IndexQuery();
        userQuery.setIndexName(indexName);
        userQuery.setType(userTypeName);
        userQuery.setObject(user);

        logger.info("UserElastic indexed: {}", esTemplate.index(userQuery));
        esTemplate.refresh(indexName);

        return user;
    }

    @Override
    public void deleteUser(UserElastic user) {
        esTemplate.getClient().prepareDelete(indexName, userTypeName, user.getId()).get();
    }

    @Override
    public CourseElastic createCourse(CourseElastic courseElastic) {
        IndexQuery userQuery = new IndexQuery();
        userQuery.setIndexName(indexName);
        userQuery.setType(userTypeName);
        userQuery.setObject(courseElastic);

        logger.info("CourseElastic indexed: {}", esTemplate.index(userQuery));
        esTemplate.refresh(indexName);

        return courseElastic;
    }

    @Override
    public List<CourseElastic> findByCourseCost(Integer cost) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.matchQuery("courseCost", (Integer)cost)).build();
        return esTemplate.queryForList(searchQuery, CourseElastic.class);
    }

    @Override
    public List<CourseElastic> findAllCourses() {
        SearchQuery getAllQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery()).build();
        return esTemplate.queryForList(getAllQuery, CourseElastic.class);
    }
}
