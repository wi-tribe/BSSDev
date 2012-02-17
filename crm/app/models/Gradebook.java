package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * Gradebook Model
 * @author stas
 */
@Entity
public class Gradebook extends Model {

    /**
     * Course ID
     */
    @Required
    public Course course_id;
    /**
     * Student ID
     */
    @Required
    public String user_id;
    /**
     * Homework ID
     */
    @Required
    public Assignment assignment_id;
    /**
     * Grade value
     */
    @Required
    public Float grade_value;
    /**
     * Entry datestamp
     */
    public Date ts;

    /**
     * Gradebook constructor
     * @param Course cid, the parent course ID
     * @param String uid, the user ID of the grade
     * @param Assignment aid, the parent assignment ID
     * @param Float grade, the grade value
     */
    public Gradebook(Course cid, String uid, Assignment aid, Float grade) {
        this.course_id = cid;
        this.user_id = uid;
        this.assignment_id = aid;
        this.grade_value = grade;
        this.ts = new Date();
    }

	/**
	 * @return the course_id
	 */
	public Course getCourse_id() {
		return course_id;
	}

	/**
	 * @param courseId the course_id to set
	 */
	public void setCourse_id(Course courseId) {
		course_id = courseId;
	}

	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * @param userId the user_id to set
	 */
	public void setUser_id(String userId) {
		user_id = userId;
	}

	/**
	 * @return the assignment_id
	 */
	public Assignment getAssignment_id() {
		return assignment_id;
	}

	/**
	 * @param assignmentId the assignment_id to set
	 */
	public void setAssignment_id(Assignment assignmentId) {
		assignment_id = assignmentId;
	}

	/**
	 * @return the grade_value
	 */
	public Float getGrade_value() {
		return grade_value;
	}

	/**
	 * @param gradeValue the grade_value to set
	 */
	public void setGrade_value(Float gradeValue) {
		grade_value = gradeValue;
	}

	/**
	 * @return the ts
	 */
	public Date getTs() {
		return ts;
	}

	/**
	 * @param ts the ts to set
	 */
	public void setTs(Date ts) {
		this.ts = ts;
	}

	/**
	 * Find all the grades for the course course_id
	 * @return List<Float> grades
	 */
	public List<Float> findGradesByCourseId() {
		List<Gradebook> gradebooks = Gradebook.find("byCourseId", course_id).fetch();
		List<Float> grades = new ArrayList<Float>();
		for(int i =0; i < gradebooks.size(); i++)
			grades.add(i, gradebooks.get(i).grade_value);
		return grades;
	}
	
	/**
	 * Find all the grades for the student with the id user_id
	 * @return List<Float> grades
	 */
	public List<Float> findGradesByStudent() {
		List<Gradebook> gradebooks = Gradebook.find("byUserId", user_id).fetch();
		List<Float> grades = new ArrayList<Float>();
		for(int i =0; i < gradebooks.size(); i++)
			grades.add(i, gradebooks.get(i).grade_value);
		return grades;
	}
	
	/**
	 * Find all the students with the grade grade_value
	 * @return List<String> userIds
	 */
	public List<String> findStudentsByGradeValue() {
		List<Gradebook> gradebooks = Gradebook.find("byGradeValue", grade_value).fetch();
		List<String> userIds = new ArrayList<String>();
		for(int i =0; i < gradebooks.size(); i++)
			userIds.add(i, gradebooks.get(i).user_id);
		return userIds;
	}
	
	/**
	 * Find all the grades for the assignment with assignment_id
	 * @return List<Float> grades
	 */
	public List<Float> findGradesByAssignmentId() {
		List<Gradebook> gradebooks = Gradebook.find("byAssignmentId", assignment_id).fetch();
		List<Float> grades = new ArrayList<Float>();
		for(int i =0; i < gradebooks.size(); i++)
			grades.add(i, gradebooks.get(i).grade_value);
		return grades;
	}
	
	/**
	 * Find all the grades for the date ts
	 * @return List<Float> grades
	 */
	public List<Float> findGradesByDate() {
		List<Gradebook> gradebooks = Gradebook.find("byTs", ts).fetch();
		List<Float> grades = new ArrayList<Float>();
		for(int i =0; i < gradebooks.size(); i++)
			grades.add(i, gradebooks.get(i).grade_value);
		return grades;
	}
}

