package controllers;

import static play.modules.pdf.PDF.renderPDF;

import java.util.List;

import models.Course;
import models.Department;
import models.User;
import play.mvc.Controller;
import play.mvc.results.NotFound;

/**
 * Courses controller
 * @author stas & alin
 */
public class Courses extends Controller {

    /**
     * Render all courses
     */
    public static void index() {
        List<Course> courses = Course.findAll();
        
        User user = User.findByEmail(session.get("userEmail"));

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
        
        render(courses);
    }

    /**
     * Render a course
     * @param id, course ID
     */
    public static void course(Long id) {
        Course course = Course.findById(id);

        if (course == null) {
            throw new NotFound(null);
        }

        render(course);
    }

    /**
     * Render course editor for course ID
     * @param id, the course ID
     */
    public static void edit(Long id) {
        Application.currentUserCan(2);
        
        List<Department> deps = Department.findAll();

        Course course = Course.findById(id);
        if (course == null) {
            throw new NotFound(null);
        }

        render(course, deps);
    }

    /**
     * Render course editor for adding new course
     */
    public static void add() {
        Application.currentUserCan(2);
        
        List<Department> deps = Department.findAll();
        
        render( deps );
    }

    /**
     * Handle POST form to update a course
     * @param id, course to be updated
     */
    public static void update(Long id) {
        Application.currentUserCan(2);
        Department dep = null;
        
        String title = params.get("course[title]", String.class);
        String content = params.get("course[content]", String.class);
        String biblio = params.get("course[biblio]", String.class);
        String teachers = params.get("course[teachers]", String.class);
        Long dep_id = params.get("course[dep_id]", Long.class);
        
        if( dep_id > 0 )
            dep = Department.findById(dep_id);
        
        Long old_id = params.get("course[title]", Long.class);
        Course course = Course.findById(id);
        if (!id.equals(old_id) && course != null) {
            course.setTitle(title);
            course.setDescription(content);
            course.setBiblio( biblio );
            
            if( dep != null )
                course.department = dep;
            
            if( teachers != null )
                course.setTeachers(teachers);
            
            course.save();
        }
        edit(id);
    }

    /**
     * Handle POST form to create a course
     */
    public static void create() {
        Application.currentUserCan(2);
        Department dep = null;

        String title = params.get("course[title]", String.class);
        String description = params.get("course[content]", String.class);
        String biblio = params.get("course[biblio]", String.class);
        String teachers = params.get("course[teachers]", String.class);
        Long dep_id = params.get("course[dep_id]", Long.class);
        
        if( dep_id > 0 )
            dep = Department.findById(dep_id);
        
        if (title.length() > 0 && description.length() > 0 && teachers != null) {
            Course course = new Course(title, description, biblio, dep);
            course.setTeachers(teachers);
            course.save();
        }

        index();
    }

    /**
     * Handle POST form to remove a course
     */
    public static void delete(Long id) {
        Course course = Course.findById(id);
        course.delete();
        index();
    }
    
    /**
     * Handle student assignment form
     */
    public static void assign() {
        Application.currentUserCan(1);
        
        User teacher = User.findByEmail(session.get("userEmail"));
        List<Course> courses = Course.findAll();
        
        if( courses.size() > 0 )
            for(int i = 0; i < courses.size(); i++ )
                if( courses.get(i) != null )
                    if( !courses.get(i).isTeacher( teacher.id ) )
                        courses.remove(i);
        
        render( courses );
    }
    
    public static void update_students() {
        Application.currentUserCan(1);
        Course course = null;
        
        String students = params.get("course[students]", String.class);
        Long course_id = params.get("course[course_id]", Long.class);
        
        if( course_id > 0 )
            course = Course.findById( course_id );
        
        if( course != null && students.length() > 0 ) {
            String[] students_arr = students.split(",");
            if( students_arr.length > 0 )
                for( int i = 0; i < students_arr.length; i++ ) {
                    User student = User.findByEmail(students_arr[i]);
                    course.setStudents( course.addStudent(student.id) );
                }
            course.save();
        }
        
        assign();
    }
	public static void export()
	{
   List<Course> courses = Course.findAll();
        
        User user = User.findByEmail(session.get("userEmail"));

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
        
        renderPDF(courses);
    }

}
