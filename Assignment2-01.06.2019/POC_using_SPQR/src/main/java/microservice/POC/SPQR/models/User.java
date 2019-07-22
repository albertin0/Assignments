package microservice.POC.SPQR.models;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//@org.springframework.data.elasticsearch.annotations.Document(indexName = "my-index3")//, type = "user"
@Document(collection = "user")
@ToString
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private Integer age;
    private String email;
    private HashMap<String,String> token;
    private List<String> role;

    public User() {
    }

    public User(User user)  {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.age = user.getAge();
        this.token = user.getToken();
        this.role = user.getRole();
    }

    public User(String firstName, String lastName, String userName, String password, Integer age,String email, String r) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.email = email;
        this.token = new HashMap<>();
        this.addRole(r);
    }

    public User(String id, String firstName, String lastName, String userName, String password, Integer age, HashMap<String,String> token, List<String> role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.email = email;
        this.token = token;
        this.role = role;
    }

    public void addToken(String id, String tok)  {
        if(this.token==null)    this.token = new HashMap<>();
        token.put(id,tok);
    }

    public void addRole(String r) {
        if(this.role==null) this.role = new ArrayList<>();
        if(!role.contains(r)) role.add(r);
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public HashMap<String, String> getToken() {
        return token;
    }

    public List<String> getRole() {
        return role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(HashMap<String, String> token) {
        this.token = token;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}
