package microservice.POC.SPQR.repository;

import microservice.POC.SPQR.models.CourseElastic;
import microservice.POC.SPQR.models.UserElastic;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
    public List<UserElastic> findByFirstNameRegEx(String firstName) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.regexpQuery("firstName", firstName)).build();
        return esTemplate.queryForList(searchQuery, UserElastic.class);
    }

    @Override
    public List<UserElastic> findByLastName(String lastName) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.matchQuery("lastName", lastName)).build();
        return esTemplate.queryForList(searchQuery, UserElastic.class);
    }

    @Override
    public List<UserElastic> findByLastNameRegEx(String lastName) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.regexpQuery("lastName", lastName)).build();
        return esTemplate.queryForList(searchQuery, UserElastic.class);
    }

    @Override
    public List<UserElastic> findByAge(Integer age) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.matchQuery("age",age)).build();
        return esTemplate.queryForList(searchQuery, UserElastic.class);
    }

    @Override
    public List<UserElastic> findByAgeRange(Integer from,Integer to) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.rangeQuery("age").from(from).to(to)).build();
        return esTemplate.queryForList(searchQuery, UserElastic.class);
    }

    @Override
    public List<UserElastic> findByEmailRegEx(String email) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.regexpQuery("email", email)).build();
        return esTemplate.queryForList(searchQuery, UserElastic.class);
    }

    @Override
    public UserElastic findByUserName(String userName) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.matchQuery("userName", userName)).build();
        List<UserElastic> list = esTemplate.queryForList(searchQuery, UserElastic.class);
        if(list.size() == 0)
            return null;
        return list.get(0);
    }

    @Override
    public UserElastic findByUserId(String userId) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.matchQuery("id", userId)).build();
        return esTemplate.queryForList(searchQuery, UserElastic.class).get(0);
    }

    @Override
    public UserElastic createUser(UserElastic userElastic) {
        IndexQuery userQuery = new IndexQuery();
        userQuery.setIndexName(indexName);
        userQuery.setType(userTypeName);
        userQuery.setId(userElastic.getId());
        userQuery.setObject(userElastic);

        logger.info("UserElastic indexed: {}", esTemplate.index(userQuery));
        esTemplate.refresh(indexName);

        return userElastic;
    }

    @Override
    public UserElastic updateUser(UserElastic userElastic) {
        IndexQuery userQuery = new IndexQuery();
        userQuery.setIndexName(indexName);
        userQuery.setType(userTypeName);
        userQuery.setId(userElastic.getId());
        userQuery.setObject(userElastic);

        logger.info("UserElastic indexed: {}", esTemplate.index(userQuery));
        esTemplate.refresh(indexName);

        return userElastic;
    }

    @Override
    public void deleteUser(UserElastic userElastic) {
        esTemplate.getClient().prepareDelete(indexName, userTypeName, userElastic.getId()).get();
    }

    @Override
    public void deleteAll() {
        esTemplate.getClient().admin().indices().prepareDelete(indexName, userTypeName).get();
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
