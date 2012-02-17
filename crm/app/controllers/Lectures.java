package controllers;

import static play.modules.pdf.PDF.renderPDF;

import java.util.List;

import models.Course;
import models.Lecture;
import models.User;
import play.mvc.Controller;
import play.mvc.results.NotFound;

/**
 * Lectures controller
 * @author OSzil
 */
public class Lectures extends Controller {

    /**
     * Render all lectures
     */
    public static void index() {
        Application.currentUserCan(0);

        User user = User.findByEmail(session.get("userEmail"));
        List<Lecture> lectures = null;
        if (user.role == 1) {
            lectures = Lecture.getByTeacherId(user.id);
        } else if (user.role == 0) {
            lectures = Lecture.getByStudentId(user.id);
        }
        
        render(lectures);
    }

    /**
     * Render a lecture
     * @param id, lecture ID
     */
    public static void lecture(Long id) {
        Lecture lecture = Lecture.findById(id);

        if (lecture == null) {
            throw new NotFound(null);
        }

        render(lecture);
    }

    /**
     * Render lecture editor for lecture ID
     * @param id, the lecture ID
     */
    public static void edit(Long id) {
        Application.currentUserCan(1);

        User teacher = User.findByEmail(session.get("userEmail"));

        Lecture lecture = Lecture.findById(id);
        if (lecture == null) {
            throw new NotFound(null);
        }

        List<Course> courses = Course.findAll();

        if (courses != null && courses.size() > 0) {
            for (int i = 0; i < courses.size(); i++) {
                if (!courses.get(i).isTeacher(teacher.id)) {
                    courses.remove(i);
                }
            }
        }

        render(lecture, courses);
    }

    /**
     * Render lecture editor for adding new lecture
     */
    public static void add(Long cid) {
        Application.currentUserCan(1);

        User teacher = User.findByEmail(session.get("userEmail"));

        List<Course> courses = Course.findAll();

        if (courses != null && courses.size() > 0) {
            for (int i = 0; i < courses.size(); i++) {
                if (!courses.get(i).isTeacher(teacher.id)) {
                    courses.remove(i);
                }
            }
        }

        render(courses);
    }

    /**
     * Handle POST form to update a lecture
     * @param id, lecture to be updated
     */
    public static void update(Long id) {
        Application.currentUserCan(1);


        Course course = null;
        Lecture lecture = null;
        User teacher = User.findByEmail(session.get("userEmail"));

        String title = params.get("lecture[title]", String.class);
        String content = params.get("lecture[content]", String.class);
        Integer no = params.get("lecture[no]", Integer.class);
        Long old_id = params.get("lecture[id]", Long.class);
        Long course_id = params.get("lecture[course]", Long.class);


        if (course_id != null && course_id > 0) {
            course = Course.findById(course_id);
            lecture = Lecture.findById(id);
        }

        if (id == old_id && lecture != null && course != null) {
            lecture.setTitle(title);
            lecture.setContent(content);
            lecture.setNo(no);
            lecture.setTeacher_id(teacher.id);
            lecture.setCourse(course);
            lecture.save();
        }
        edit(id);
    }

    /**
     * Handle POST form to create a lecture
     * @param id, lecture to be created 
     */
    public static void create(Long cid) {
        Application.currentUserCan(1);

        Course course = null;
        User teacher = User.findByEmail(session.get("userEmail"));

        String title = params.get("lecture[title]", String.class);
        String content = params.get("lecture[content]", String.class);
        Integer no = params.get("lecture[no]", Integer.class);
        Long course_id = params.get("lecture[course]", Long.class);

        if (course_id > 0) {
            course = Course.findById(course_id);
        }

        if (title.length() > 0 && content.length() > 0 && no > 0 && course != null) {
            Lecture lecture = new Lecture(title, content, no, teacher.id, course);
            lecture.save();
        }

        index();
    }

    /**
     * Delete lecture
     * @param id, lecture to be deleted
     */
    public static void delete(Long id) {
        Application.currentUserCan(1);

        Lecture lecture = Lecture.findById(id);

        if (lecture != null) {
            lecture.delete();
        }

        index();
    }
	public static void export() {
        Application.currentUserCan(0);

        User user = User.findByEmail(session.get("userEmail"));
        List<Lecture> lectures = null;
        if (user.role == 1) {
            lectures = Lecture.getByTeacherId(user.id);
        } else if (user.role == 0) {
            lectures = Lecture.getByStudentId(user.id);
        }
        
        renderPDF(lectures);
    }
}
