package models;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * Responses model
 * @author alin
 */
@Entity
public class Response extends Model {

    /**
     * Course id
     */
    @Required
    @ManyToOne
    public Course course;
    /**
     * Assignment id
     */
    @Required
    @ManyToOne
    public Assignment assignment;
    /**
     * Student id
     */
    @Required
    public String student_id;
    /**
     * Response
     */
    @Required
    @Lob
    @MaxSize(10000)
    public String content;
    /**
     * If it has already been graded
     */
    @Required
    public Boolean graded;
    /**
     * Teacher's comment
     */
    @Required
    @Lob
    @MaxSize(10000)
    public String comment;
    /**
     * Grade for the assignment
     */
    @Required
    public float grade;

    /**
     * @param cid
     * @param tid
     * @param aid
     * @param sid
     */
    public Response(Course cid, Assignment aid, String sid) {
        this.course = cid;
        this.assignment = aid;
        this.student_id = sid;
        this.comment = null;
        this.grade = 4;
        this.content = null;
        this.graded = false;
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
	 * @return the assignment
	 */
	public Assignment getAssignment() {
		return assignment;
	}

	/**
	 * @param assignment the assignment to set
	 */
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	/**
	 * @return the student_id
	 */
	public String getStudent_id() {
		return student_id;
	}

	/**
	 * @param studentId the student_id to set
	 */
	public void setStudent_id(String studentId) {
		student_id = studentId;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the graded
	 */
	public Boolean getGraded() {
		return graded;
	}

	/**
	 * @param graded the graded to set
	 */
	public void setGraded(Boolean graded) {
		this.graded = graded;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
    
    
}