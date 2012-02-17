package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * Lecture Model
 * @author cristina
 *
 */
@Entity
public class Lecture extends Model {

    /**
     * Lecture date
     */
    @Required
    public Date ts;
    /**
     * Lecture title
     */
    @Required
    public String title;
    /**
     * Lecture content
     */
    @Lob
    @Required
    @MaxSize(10000)
    public String content;
    /**
     * Lecture no
     */
    @Required
    public Integer no;
    /**
     * Lecture teacher id
     */
    @Required
    public String teacher_id;
    /**
     * Parent Course
     */
    @Required
    @ManyToOne
    public Course course;
    /**
     * Child Assignment
     */
    @Required
    @ManyToOne
    public Assignment assignments;

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return ts;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date ts) {
        this.ts = ts;
    }

    /**
     * @param title
     * @param no
     * @param teacherId
     * @param course
     */
    public Lecture(String title, String c, Integer no, String teacherId, Course course) {
        this.title = title;
        this.content = c;
        this.no = no;
        this.teacher_id = teacherId;
        this.course = course;
        this.ts = new Date();
    }

    /**
     * String getContent()
     * @return String lecture content
     */
    public String getContent() {
        return content;
    }

    /**
     * void setContent(String content)
     * @param String lecture content 
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the no
     */
    public Integer getNo() {
        return no;
    }

    /**
     * @param no the no to set
     */
    public void setNo(Integer no) {
        this.no = no;
    }

    /**
     * @return the teacher_id
     */
    public String getTeacher_id() {
        return teacher_id;
    }

    /**
     * @param teacherId the teacher_id to set
     */
    public void setTeacher_id(String teacherId) {
        teacher_id = teacherId;
    }

    /**
     * @return the course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Render lectures by course id
     * @param idc, id of course
     */
    public static List<Lecture> getByCourseId(Long idc) {
        Course course = Course.findById(idc);
        List<Lecture> lectures = course.getLectures();
        return lectures;
    }

    /**
     * Render lectures by teacher id
     * @param idt, id of teacher
     */
    public static List<Lecture> getByTeacherId(String idt) {
        return Lecture.find("byTeacher_id", idt).fetch();
    }

    /**
     * Render lectures by student id
     * @param idt, id of student
     */
    public static List<Lecture> getByStudentId(String idt) {
        return Lecture.find("byStudent_id", idt).fetch();
    }
}
