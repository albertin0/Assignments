package microservice.POC.SPQR.models;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> token;
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

    public User(String firstName, String lastName, String userName, String password, Integer age, String r) {
        //this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.addRole(r);
    }

    public User(String id, String firstName, String lastName, String userName, String password, Integer age, List<String> token, List<String> role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.token = token;
        this.role = role;
    }

    public void addToken(String t)  {
        if(this.token==null)    this.token = new ArrayList<>();
        token.add(t);
    }

    public void addRole(String r) {
        if(this.role==null) this.role = new ArrayList<>();
        if(!role.contains(r)) role.add(r);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getToken() {
        return token;
    }

    public void setToken(List<String> token) {
        this.token = token;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
