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
 * Course Model
 * @author alin
 */
@Entity
public class Course extends Model {

    /**
     * Course title
     */
    @Required
    public String title;
    /**
     * Course description
     */
    @Lob
    @Required
    @MaxSize(10000)
    public String description;
    /**
     * Course creation date
     */
    @Required
    public Date timestamp;
    /**
     * Course teachers
     */
    @Required
    public String teachers;
    /**
     * Students list
     */
    @Required
    public String students;
    /**
     * Course bibliography
     */
    @Required
    public String biblio;
    /**
     * Lectures in course
     */
    @Required
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    public List<Lecture> lectures;
    /**
     * Assignments for the course
     */
    @Required
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    public List<Assignment> assignments;
    
    /**
     * Assignments for the course
     */
    @Required
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    public List<Response> responses;
    
    /**
     * Department for the course
     */
    @Required
    @ManyToOne
    public Department department;

    /**
     * Course constructor
     * @param String title, the title of the course
     * @param String description, syllabus
     * @param List<String> biblio, course bibliography
     */
    public Course(String title, String description, String biblio, Department dep) {

        this.title = title;
        this.description = description;
        this.biblio = biblio;
        this.timestamp = new Date();
        this.lectures = null;
        this.assignments = null;
        this.department = dep;
    }

    /**
     * Add teacher to course
     * @param String teacher_id, teacher id
     * @return String, new list of teachers for the course
     */
    public String addTeacher(String teacher_id) {

        this.teachers += "," + teacher_id;

        return this.teachers;
    }

    /**
     * Remove teacher from the course
     * @param String teacher_id, the md5 hash for the teacher
     * @return String, new list of teachers for the course
     */
    public String delTeacher(String teacher_id) {
        String[] arr_teachers = this.teachers.split(",");
        String new_teachers = null;
        int i = 0;

        while (i < arr_teachers.length) {
            if (arr_teachers[i].equals(teacher_id)) {
                continue;
            } else {
                new_teachers += arr_teachers[i];
            }
        }

        return new_teachers;
    }

    /**
     * Add students to course
     * @param String student_id, the id of the student to be added to course
     */
    public String addStudent(String student_id) {
        if( this.students == null )
            this.students = student_id;
        else if( this.students.endsWith(",") )
            this.students += student_id;
        else
            this.students += "," + student_id;
        
        return this.students;
    }

    /**
     * Remove student from the course
     * @param String student_id, the md5 hash for the teacher
     * @return String, new list of students for the course
     */
    public String delStudent(String student_id) {
        String[] arr_students = this.students.split(",");
        String new_students = null;
        int i = 0;

        while (i < arr_students.length) {
            if (arr_students[i].equals(student_id)) {
                continue;
            } else {
                new_students += arr_students[i];
            }
        }

        return new_students;
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the teachers
     */
    public String[] getTeachers() {
        if( teachers != null && teachers.length() > 0 )
            return teachers.split(",");
        else
            return null;
    }

    /**
     * @param new_teachers the teachers to set
     */
    public void setTeachers(String new_teachers) {
        String teacher_ids = "";
        
        String[] teachers_arr = new_teachers.split(",");
        
        if( teachers_arr != null ) {
            for( String t: teachers_arr ) {
                User teacher = User.findByEmail(t);
                if( teacher != null )
                    teacher_ids += teacher.id + ",";
            }
            // Remove trailing comma
            if( teacher_ids.endsWith(",") )
                teacher_ids = teacher_ids.substring(0, teacher_ids.length() - 1);
        }
        
        if( this.teachers == null )
            this.teachers = teacher_ids;
        else if( this.teachers.endsWith(",") )
            this.teachers += teacher_ids;
        else
            this.teachers += "," + teacher_ids;
    }

    /**
     * @return the students
     */
    public String getStudents() {
        if( students != null )
            return students;
        else
            return null;
    }

    /**
     * @param students the students to set
     */
    public void setStudents(String students) {
        this.students = students;
    }

    /**
     * @return the biblio
     */
    public String getBiblio() {
        return biblio;
    }

    /**
     * @param biblio the biblio to set
     */
    public void setBiblio(String biblio) {
        this.biblio = biblio;
    }

    /**
     * @return the lectures
     */
    public List<Lecture> getLectures() {
        return lectures;
    }

    /**
     * @param lectures the lectures to set
     */
    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    /**
     * @return the assignments
     */
    public List<Assignment> getAssignments() {
        return assignments;
    }

    /**
     * @param assignments the assignments to set
     */
    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    /**
     * @param teacher id, the id of the teacher to check
     */
    public Boolean isTeacher(String tid) {
        String[] arr_teachers = null;
        
        if( this.getTeachers() != null && this.getTeachers().length > 0 ) {
            arr_teachers = this.getTeachers();
            int i = 0;
            while (i < arr_teachers.length) {
                if (arr_teachers[i].equals(tid))
                    return true;
                i++;
            }
        }
        return false;
    }
    
    /**
     * @param student id, the id of the student to check
     */
    public Boolean isStudent(String sid) {
        String[] arr_students = null;
        
        if( this.getStudents() != null && this.getStudents().length() > 0 ) {
            arr_students = this.getStudents().split(",");
            int i = 0;
            while (i < arr_students.length) {
                if (arr_students[i].equals(sid))
                    return true;
                i++;
            }
        }
        return false;
    }
}
