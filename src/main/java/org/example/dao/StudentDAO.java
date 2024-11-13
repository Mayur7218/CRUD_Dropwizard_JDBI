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

    @SqlUpdate("INSERT INTO students (name, age, grade) VALUES (:name, :age, :grade)")
    void createStudent(@BindBean Student student);

    @SqlQuery("SELECT * FROM students WHERE id = :id")
    @RegisterBeanMapper(Student.class)
    Student getStudentById(@Bind("id") long id);

    @SqlQuery("SELECT * FROM students")
    @RegisterBeanMapper(Student.class)
    List<Student> getAllStudents();

    @SqlUpdate("UPDATE students SET name = :name, age = :age, grade = :grade WHERE id = :id")
    void updateStudent(@BindBean Student student);

    @SqlUpdate("DELETE FROM students WHERE id = :id")
    void deleteStudent(@Bind("id") long id);


    @SqlUpdate("INSERT INTO student_courses (student_id, course_id) VALUES (:studentId, :courseId)")
    void addCourseToStudent(@Bind("studentId") long studentId, @Bind("courseId") long courseId);

    @SqlUpdate("DELETE FROM student_courses WHERE student_id = :studentId AND course_id = :courseId")
    void removeCourseFromStudent(@Bind("studentId") long studentId, @Bind("courseId") long courseId);

    @SqlQuery("SELECT c.* FROM courses c JOIN student_courses sc ON c.id = sc.course_id WHERE sc.student_id = :studentId")
    @RegisterBeanMapper(Course.class)
    List<Course> getCoursesByStudentId(@Bind("studentId") long studentId);

}
