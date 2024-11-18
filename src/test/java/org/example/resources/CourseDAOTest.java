package org.example.resources;

import net.bytebuddy.jar.asm.Handle;
import org.example.dao.CourseDAO;
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
public class CourseDAOTest {

    private CourseDAO courseDAO;

    @Mock
    private Handle handle;

    private Course course;

    @BeforeEach
    public void setUp() {
        courseDAO = mock(CourseDAO.class);
        course = new Course();
        course.setId(101L);
        course.setCourseName("Mathematics");
        course.setDuration(6);
    }

    @Test
    public void testGetCourseById() {
        when(courseDAO.getCourseById(101L)).thenReturn(course);

        Course result = courseDAO.getCourseById(101L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Mathematics", result.getCourseName());
        Assertions.assertEquals(6, result.getDuration());
    }

    @Test
    public void testCreateCourse() {
        when(courseDAO.createCourse(course)).thenReturn(1);
        int result = courseDAO.createCourse(course);

        Assertions.assertEquals(1, result);
    }

    @Test
    public void testUpdateCourse() {
        when(courseDAO.updateCourse(course)).thenReturn(1);
        int result = courseDAO.updateCourse(course);
        Assertions.assertEquals(1, result);
    }

    @Test
    public void testDeleteCourse() {
        when(courseDAO.deleteCourse(101L)).thenReturn(1);

        int result = courseDAO.deleteCourse(101L);

        Assertions.assertEquals(1, result);
    }

    @Test
    public void testGetAllCourses() {
        List<Course> courses = Collections.singletonList(course);
        when(courseDAO.getAllCourses()).thenReturn(courses);

        List<Course> result = courseDAO.getAllCourses();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Mathematics", result.get(0).getCourseName());
    }

    @Test
    public void testAddStudentToCourse() {
        doNothing().when(courseDAO).addStudentToCourse(1L, 101L);
        courseDAO.addStudentToCourse(1L, 101L);

        verify(courseDAO, times(1)).addStudentToCourse(1L, 101L);
    }

    @Test
    public void testRemoveStudentFromCourse() {
        doNothing().when(courseDAO).removeStudentFromCourse(1L, 101L);

        courseDAO.removeStudentFromCourse(1L, 101L);
        verify(courseDAO, times(1)).removeStudentFromCourse(1L, 101L);
    }

    @Test
    public void testGetStudentsByCourseId() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Mayur");

        List<Student> students = Collections.singletonList(student);
        when(courseDAO.getStudentsByCourseId(101L)).thenReturn(students);
        List<Student> result = courseDAO.getStudentsByCourseId(101L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Mayur", result.get(0).getName());
    }
}
