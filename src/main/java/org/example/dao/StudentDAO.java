package org.example.dao;

import org.example.models.Course;
import org.example.models.Student;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;

public interface StudentDAO {

    @SqlQuery("SELECT * FROM students")
    @RegisterBeanMapper(Student.class)
    List<Student> getAllStudents();

    @SqlQuery("SELECT * FROM students WHERE id = :id")
    @RegisterBeanMapper(Student.class)
    Student getStudentById(@Bind("id") long id);

    // Create method returns the created student, including the generated ID.
    @SqlUpdate("INSERT INTO students (name, age, grade) VALUES (:name, :age, :grade)")
    @RegisterBeanMapper(Student.class)
    int createStudent(@BindBean Student student);

    // Update method returns the updated student.
    @SqlUpdate("UPDATE students SET name = :name, age = :age, grade = :grade WHERE id = :id")
    @RegisterBeanMapper(Student.class)
    int updateStudent(@BindBean Student student);

    // Delete method returns true if the student was deleted successfully.
    @SqlUpdate("DELETE FROM students WHERE id = :id")
    int deleteStudent(@Bind("id") long id);


    @SqlUpdate("INSERT INTO student_courses (student_id, course_id) VALUES (:studentId, :courseId)")
    void addCourseToStudent(@Bind("studentId") long studentId, @Bind("courseId") long courseId);

    @SqlUpdate("DELETE FROM student_courses WHERE student_id = :studentId AND course_id = :courseId")
    void removeCourseFromStudent(@Bind("studentId") long studentId, @Bind("courseId") long courseId);

    @SqlQuery("SELECT c.* FROM courses c JOIN student_courses sc ON c.id = sc.course_id WHERE sc.student_id = :studentId")
    @RegisterBeanMapper(Course.class)
    List<Course> getCoursesByStudentId(@Bind("studentId") long studentId);


    @SqlQuery("SELECT * FROM students " +
            "WHERE age BETWEEN :ageMin AND :ageMax " +
            "AND grade BETWEEN :gradeMin AND :gradeMax " +
            "ORDER BY CASE WHEN :sortField = 'name' THEN name END " +
            "        , CASE WHEN :sortField = 'age' THEN age END " +
            "        , CASE WHEN :sortField = 'grade' THEN grade END " +
            "LIMIT :limit OFFSET :offset")
    @RegisterBeanMapper(Student.class)
    List<Student> getFilteredSortedPaginatedStudents(
            @Bind("sortField") String sortField,
            @Bind("ageMin") int ageMin,
            @Bind("ageMax") int ageMax,
            @Bind("gradeMin") double gradeMin,
            @Bind("gradeMax") double gradeMax,
            @Bind("limit") int limit,
            @Bind("offset") int offset
    );
}
