package org.example.dao;

import org.example.models.Course;
import org.example.models.Student;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;

public interface CourseDAO {

    @SqlUpdate("INSERT INTO courses (course_name, duration) VALUES (:courseName, :duration)")
    int createCourse(@BindBean Course course);

    @SqlQuery("SELECT * FROM courses WHERE id = :id")
    @RegisterBeanMapper(Course.class)
    Course getCourseById(@Bind("id") long id);

    @SqlQuery("SELECT * FROM courses")
    @RegisterBeanMapper(Course.class)
    List<Course> getAllCourses();

    @SqlUpdate("UPDATE courses SET course_name = :courseName, duration = :duration WHERE id = :id")
    int updateCourse(@BindBean Course course);

    @SqlUpdate("DELETE FROM courses WHERE id = :id")
    int deleteCourse(@Bind("id") long id);

    @SqlUpdate("INSERT INTO student_courses (student_id, course_id) VALUES (:studentId, :courseId)")
    void addStudentToCourse(@Bind("studentId") long studentId, @Bind("courseId") long courseId);

    @SqlUpdate("DELETE FROM student_courses WHERE student_id = :studentId AND course_id = :courseId")
    void removeStudentFromCourse(@Bind("studentId") long studentId, @Bind("courseId") long courseId);

    @SqlQuery("SELECT s.* FROM students s JOIN student_courses sc ON s.id = sc.student_id WHERE sc.course_id = :courseId")
    @RegisterBeanMapper(Student.class)
    List<Student> getStudentsByCourseId(@Bind("courseId") long courseId);

    // New method to handle pagination, filtering, and sorting
    @SqlQuery("""
        SELECT * FROM courses
        WHERE (:courseName IS NULL OR course_name LIKE :courseName)
        ORDER BY CASE WHEN :sortOrder = 'asc' THEN :sortBy END ASC,
                 CASE WHEN :sortOrder = 'desc' THEN :sortBy END DESC
        LIMIT :limit OFFSET :offset
    """)
    @RegisterBeanMapper(Course.class)
    List<Course> getCoursesWithPaginationAndFilters(
            @Bind("courseName") String courseName,
            @Bind("sortBy") String sortBy,
            @Bind("sortOrder") String sortOrder,
            @Bind("limit") int limit,
            @Bind("offset") int offset
    );
}
