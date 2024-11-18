package org.example.resources;

import jersey.repackaged.org.objectweb.asm.Handle;
import org.example.dao.StudentDAO;
import org.example.models.Course;
import org.example.models.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentDAOTest {

    private StudentDAO studentDAO;

    @Mock
    private Handle handle;

    private Student student;

    @BeforeEach
    public void setUp() {
        studentDAO = mock(StudentDAO.class);
        student = new Student();
        student.setId(3L);
        student.setName("Mayur");
        student.setAge(20);
        student.setGrade(2.1);
    }

    @Test
    public void testGetStudentById() {
        // Mocking the DAO method to return a student when getStudentById is called
        when(studentDAO.getStudentById(1L)).thenReturn(student);

        // Call the DAO method
        Student result = studentDAO.getStudentById(1L);

        // Assert the result
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Mayur", result.getName());
        Assertions.assertEquals(20, result.getAge());
    }

    @Test
    public void testCreateStudent() {
        when(studentDAO.createStudent(student)).thenReturn(1);
        int result = studentDAO.createStudent(student);
        Assertions.assertEquals(1, result);
    }

    @Test
    public void testUpdateStudent() {
        when(studentDAO.updateStudent(student)).thenReturn(1);
        int result = studentDAO.updateStudent(student);
        Assertions.assertEquals(1, result);
    }

    @Test
    public void testDeleteStudent() {
        when(studentDAO.deleteStudent(1L)).thenReturn(1);
        int result = studentDAO.deleteStudent(1L);
        Assertions.assertEquals(1, result);
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = Collections.singletonList(student);
        when(studentDAO.getFilteredSortedPaginatedStudents(anyString(), anyInt(), anyInt(), anyDouble(), anyDouble(), anyInt(), anyInt()))
                .thenReturn(students);

        List<Student> result = studentDAO.getFilteredSortedPaginatedStudents("name", 18, 25, 80.0, 100.0, 10, 0);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Mayur", result.getFirst().getName());
    }

    @Test
    public void testAddCourseToStudent() {
        doNothing().when(studentDAO).addCourseToStudent(1L, 101L);
        studentDAO.addCourseToStudent(1L, 101L);
        verify(studentDAO, times(1)).addCourseToStudent(1L, 101L);
    }

    @Test
    public void testRemoveCourseFromStudent() {
        doNothing().when(studentDAO).removeCourseFromStudent(1L, 101L);
        studentDAO.removeCourseFromStudent(1L, 101L);
        verify(studentDAO, times(1)).removeCourseFromStudent(1L, 101L);
    }

    @Test
    public void testGetCoursesByStudentId() {
        Course course = new Course();
        course.setId(101L);
        course.setCourseName("Mathematics");
        List<Course> courses = Collections.singletonList(course);
        when(studentDAO.getCoursesByStudentId(1L)).thenReturn(courses);

        List<Course> result = studentDAO.getCoursesByStudentId(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Mathematics", result.getFirst().getCourseName());
    }
}
