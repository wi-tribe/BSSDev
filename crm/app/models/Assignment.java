package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * Assignment Model
 * @author cristina
 *
 */
@Entity
public class Assignment extends Model {

    /**
     * Parent course
     */
    @Required
    @ManyToOne
    public Course course;
    /**
     * Assignment lecture_ids list
     */
    @Required
    @OneToMany(mappedBy = "assignments", cascade = CascadeType.ALL)
    public List<Lecture> lecture;
    /**
     * Assignment type
     */
    @Required
    public String type;
    /**
     * Assignment problem
     */
    @Required
    @Lob
    @MaxSize(10000)
    public String problem;
    /**
     * Assignment deadline
     */
    @Required
    public Date deadline = new Date();
    /**
     * Assignment creation time
     */
    @Required
    public Date timeStamp = new Date();
    /**
     * Assignment responses
     */
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
    public List<Response> responses;
    /**
     * Assignment grade
     */
    public float grade;

    /**
     * @param course
     * @param lectureIds
     * @param type
     * @param problem
     * @param deadline
     * @param responses
     */
    public Assignment(Course course, List<Lecture> lectures, String type, String problem,
            Date deadline, List<Response> responses) {
        this.course = course;
        this.lecture = lectures;
        this.type = type;
        this.problem = problem;
        this.deadline = deadline;
        this.responses = responses;
        this.grade = (float) 4.0;
    }

    /**
     * @param lectureIds
     * @param type
     * @param problem
     * @param deadline
     * @param responses
     * @param grade
     */
    public Assignment(List<Lecture> lectures, String type, String problem,
            Date deadline, List<Response> responses, float grade) {
        this.lecture = lectures;
        this.type = type;
        this.problem = problem;
        this.deadline = deadline;
        this.responses = responses;
        this.grade = grade;
    }

    /**
     * @param course
     * @param lectureIds
     * @param type
     * @param problem
     * @param deadline
     */
    public Assignment(Course course, List<Lecture> lectures, String type, String problem, Date deadline) {
        this.course = course;
        this.lecture = lectures;
        this.type = type;
        this.problem = problem;
        this.deadline = deadline;
        this.responses = null;
        this.grade = (float) 0.0;
    }

    /**
     * @param course
     * @param lectureIds
     * @param type
     * @param problem
     * @param deadline
     */
    public Assignment(Course course, Lecture l, String type, String problem, Date deadline) {
        this.course = course;
        this.lecture.add(l);
        this.type = type;
        this.problem = problem;
        this.deadline = deadline;
        this.responses = null;
        this.grade = (float) 0.0;
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
     * @return timeStamp
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timestamp to set
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * @return the lecture_ids
     */
    public List<Lecture> getLectures() {
        return this.lecture;
    }

    /**
     * @param lectureIds the lecture_ids to set
     */
    public void setLectures(Lecture l) {
        if (l != null) {
            this.lecture.add(l);
        }
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the problem
     */
    public String getProblem() {
        return problem;
    }

    /**
     * @param problem the problem to set
     */
    public void setProblem(String problem) {
        this.problem = problem;
    }

    /**
     * @return the deadline
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * @param deadline the deadline to set
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /**
     * @return the responses
     */
    public List<Response> getResponses() {
        return responses;
    }

    /**
     * @param responses the responses to set
     */
    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    /**
     * @return the grade
     */
    public float getGrade() {
        return grade;
    }

    /**
     * @param grade the grade to set
     */
    public void setGrade(float grade) {
        this.grade = grade;
    }
    
    public List<Assignment> getByCourseId(Long course_id) {
        List<Assignment> assignments = null;
        Course c = Course.findById(course_id);
        if (c != null) {
            assignments = c.getAssignments();
        }
        return assignments;
    }
}
