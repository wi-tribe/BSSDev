package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * Department main class
 * @author stas
 */
@Entity
public class Department extends Model {

    /**
     * Title of the department
     */
    @Required
    public String title;
    /**
     * Keyword of the department
     */
    @Required
    @MaxSize(3)
    public String keyword;
    /**
     * Courses list
     */
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    public List<Course> course_ids;

    /**
     * Department constructor
     */
    public Department(String t, String kw) {
        this.title = t;
        this.keyword = kw;
    }

    /**
     * Get courses for one teacher
     */
    public List<Course> getByTeacherId(String tid) {
        List<Course> courses = null;
        int i = 0;
        
        if( course_ids != null )
            for (Course c: course_ids) {
                if (c.isTeacher(tid)) {
                    courses.add(c);
                }
            }
        
        return courses;
    }
}
