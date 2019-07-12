package microservice.POC.SPQR.models;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "my_index", type = "user")
@ToString
public class CourseElastic {

    @Id
    private String courseId;
    private String courseName;
    private Integer courseCost;
    private String courseCode;

    public CourseElastic() {
    }

    public CourseElastic(String courseName, Integer courseCost, String courseCode) {
        this.courseName = courseName;
        this.courseCost = courseCost;
        this.courseCode = courseCode;
    }

    public CourseElastic(String courseId, String courseName, Integer courseCost, String courseCode) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCost = courseCost;
        this.courseCode = courseCode;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getCourseCost() {
        return courseCost;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseCost(Integer courseCost) {
        this.courseCost = courseCost;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
