package controllers;

import static play.modules.pdf.PDF.renderPDF;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Assignment;
import models.Course;
import models.Lecture;
import models.User;
import play.mvc.Controller;
import play.mvc.results.NotFound;

/**
 * Assignment controller
 * @author Asim Nawaz
 *
 */

public class Assignments extends Controller {

    /**
     * Render all assignments
     */
    public static void index() {
        Application.currentUserCan(0);

        User user = User.findByEmail(session.get("userEmail"));
        List<Assignment> assignments = new ArrayList<Assignment>();
        List<Course> courses = Course.findAll();

        if (courses != null && courses.size() > 0) {
            for (int i = 0; i < courses.size(); i++) {
                if (user.role == 1) {
                    if (!courses.get(i).isTeacher(user.id))
                        courses.remove(i);
                }
                if (user.role == 0) {
                    if (!courses.get(i).isStudent(user.id))
                        courses.remove(i);
                }
            }
        }

        if (courses != null && courses.size() > 0) {
            for (int i = 0; i < courses.size(); i++) {
                List<Assignment> as_list = Assignment.find("byCourse", courses.get(i)).fetch();

                if (as_list != null && as_list.size() > 0) {
                    for (int j = 0; j < as_list.size(); j++) {
                        assignments.add(as_list.get(j));
                    }
                }
            }
        }
        
        render(assignments);
    }

    /**
     * Render an assignment
     * @param id, the assignment ID
     */
    public static void assignment(Long id) {
        Application.currentUserCan(0);

        Assignment assignment = Assignment.findById(id);

        if (assignment == null) {
            throw new NotFound(null);
        }

        render(assignment);
    }

    /**
     * Render assignment editor for assignment ID
     * @param id, the assignment ID
     */
    public static void edit(Long id) {
        Application.currentUserCan(1);

        Assignment assignment = Assignment.findById(id);
        Course course = null;
        List<Lecture> lectures = null;
        
        if (assignment == null) {
            throw new NotFound(null);
        }
        
        course = Course.findById(assignment.course.id);
        if (course != null) {
            lectures = Lecture.find("byCourse", course).fetch();
        }

        render(assignment, course, lectures);
    }

    /**
     * Render assignment editor for adding new assignment
     */
    public static void add() {
        Application.currentUserCan(1);

        Long course_id = params.get("assignment[course_id]", Long.class);

        User user = User.findByEmail(session.get("userEmail"));
        List<Course> courses = Course.findAll();

        for (int i = 0; i < courses.size(); i++) {
            if (user.role <= 1) {
                if (user.role == 1) {
                    if (!courses.get(i).isTeacher(user.id))
                        courses.remove(i);
                }
                if (user.role == 0) {
                    if (!courses.get(i).isStudent(user.id))
                        courses.remove(i);
                }
            }
        }

        if (course_id != null && course_id > 0) {
            add(course_id, courses);
        }

        render(courses);
    }

    /**
     * Render assignment editor for adding new assignment by course
     */
    static void add(Long course_id, List<Course> courses) {
        Application.currentUserCan(1);

        Course course = null;
        List<Lecture> lectures = null;

        if (course_id != null && course_id > 0) {
            course = Course.findById(course_id);
        }

        if (course != null) {
            lectures = Lecture.find("byCourse", course).fetch();
        }

        render(course, lectures, courses);
    }

    /**
     * Handle POST form to update an assignment
     * @param id, assignment to be updated
     */
    public static void update(Long id) throws ParseException {
        Application.currentUserCan(1);

        Course course = null;
        List<Lecture> lecture = new ArrayList<Lecture>();
        Date date = null;

        Long course_id = params.get("assignment[course_id]", Long.class);
        Long lecture_id = params.get("assignment[lecture_id]", Long.class);
        String problem = params.get("assignment[content]", String.class);
        String deadline = params.get("assignment[deadline]", String.class).replace("T", " ");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar currentDate = Calendar.getInstance();

        if (sdf.parse(deadline) != null && sdf.parse(deadline).before(currentDate.getTime())) {
            date = sdf.parse(deadline);
        }

        if (course_id != null && course_id > 0) {
            course = Course.findById(course_id);
        }

        if (course != null) {
            lecture.add((Lecture) Lecture.findById(lecture_id));
        }

        if (problem.length() > 0 && date != null && lecture != null) {
            Assignment assignment = Assignment.findById(id);
            assignment.problem = problem;
            assignment.deadline = date;
            assignment.save();
        }
        
        edit(id);
    }

    /**
     * Handle POST form to create an assignment
     */
    public static void create() throws ParseException {
        Application.currentUserCan(1);

        Course course = null;
        List<Lecture> lecture = new ArrayList<Lecture>();
        Date date = null;

        Long course_id = params.get("assignment[course_id]", Long.class);
        Long lecture_id = params.get("assignment[lecture_id]", Long.class);
        String type = params.get("assignment[type]", String.class);
        String problem = params.get("assignment[content]", String.class);
        String deadline = params.get("assignment[deadline]", String.class).replace("T", " ");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar currentDate = Calendar.getInstance();

//        if (sdf.parse(deadline) != null && sdf.parse(deadline).before(currentDate.getTime())) {
            date = new Date();
//        }

        if (course_id != null && course_id > 0) {
            course = Course.findById(course_id);
        }

        if (course != null) {
            lecture.add((Lecture) Lecture.findById(lecture_id));
        }

        if (type.length() > 0 && problem.length() > 0 && date != null && lecture != null) {
            Assignment assignment = new Assignment(course, lecture, type, problem, date);
            assignment.save();
        }

        index();
    }
    
    /**
     * Handle POST form to remove an assignment
     */
    public static void delete(Long id) {
        Assignment as = Assignment.findById(id);
        as.delete();
        index();
    }
	public static void export() {
        Application.currentUserCan(0);

        User user = User.findByEmail(session.get("userEmail"));
        List<Assignment> assignments = new ArrayList<Assignment>();
        List<Course> courses = Course.findAll();

        if (courses != null && courses.size() > 0) {
            for (int i = 0; i < courses.size(); i++) {
                if (user.role == 1) {
                    if (!courses.get(i).isTeacher(user.id))
                        courses.remove(i);
                }
                if (user.role == 0) {
                    if (!courses.get(i).isStudent(user.id))
                        courses.remove(i);
                }
            }
        }

        if (courses != null && courses.size() > 0) {
            for (int i = 0; i < courses.size(); i++) {
                List<Assignment> as_list = Assignment.find("byCourse", courses.get(i)).fetch();

                if (as_list != null && as_list.size() > 0) {
                    for (int j = 0; j < as_list.size(); j++) {
                        assignments.add(as_list.get(j));
                    }
                }
            }
        }
        
        renderPDF(assignments);
    }
}

