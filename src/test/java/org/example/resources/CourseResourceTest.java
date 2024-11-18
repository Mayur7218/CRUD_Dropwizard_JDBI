package org.example.resources;
import jakarta.ws.rs.core.Response;
import org.example.dao.CourseDAO;
import org.example.models.Course;
import org.example.models.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CourseResourceTest {

    @InjectMocks
    private CourseResource courseResource;

    @Mock
    private CourseDAO courseDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCourse() {
        long courseId = 1L;
        Course course = new Course();
        course.setId(courseId);
        course.setCourseName("Math");
        course.setDuration(10);

        when(courseDAO.getCourseById(courseId)).thenReturn(course);

        Response response = courseResource.getCourse(courseId);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        Course returnedCourse = (Course) response.getEntity();
        assertEquals(courseId, returnedCourse.getId());
    }

    @Test
    public void testGetCourseNotFound() {
        long courseId = 1L;
        when(courseDAO.getCourseById(courseId)).thenReturn(null);

        Response response = courseResource.getCourse(courseId);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetAllCourses() {
        List<Course> courses = Arrays.asList(new Course(), new Course());

        when(courseDAO.getCoursesWithPaginationAndFilters(null, "id", "asc", 10, 0)).thenReturn(courses);

        Response response = courseResource.getAllCourses(1, 10, null, "id", "asc");

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals(courses.size(), ((List<?>) response.getEntity()).size());
    }

    @Test
    public void testGetAllCoursesNoContent() {
        when(courseDAO.getCoursesWithPaginationAndFilters(null, "id", "asc", 10, 0)).thenReturn(Arrays.asList());

        Response response = courseResource.getAllCourses(1, 10, null, "id", "asc");

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void testCreateCourse() {
        Course course = new Course();
        course.setCourseName("Math");
        course.setDuration(10);

        when(courseDAO.createCourse(course)).thenReturn(1);

        Response response = courseResource.createCourse(course);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(course, response.getEntity());
    }

    @Test
    public void testCreateCourseFail() {
        Course course = new Course();
        course.setCourseName("Math");
        course.setDuration(10);

        when(courseDAO.createCourse(course)).thenReturn(0);

        Response response = courseResource.createCourse(course);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateCourse() {
        long courseId = 1L;
        Course course = new Course();
        course.setCourseName("Math");
        course.setDuration(10);
        course.setId(courseId);

        when(courseDAO.updateCourse(course)).thenReturn(1);

        Response response = courseResource.updateCourse(courseId, course);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(course, response.getEntity());
    }

    @Test
    public void testUpdateCourseFail() {
        long courseId = 1L;
        Course course = new Course();
        course.setCourseName("Math");
        course.setDuration(10);
        course.setId(courseId);

        when(courseDAO.updateCourse(course)).thenReturn(0);

        Response response = courseResource.updateCourse(courseId, course);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeleteCourse() {
        long courseId = 1L;

        when(courseDAO.deleteCourse(courseId)).thenReturn(1);

        Response response = courseResource.deleteCourse(courseId);

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeleteCourseFail() {
        long courseId = 1L;

        when(courseDAO.deleteCourse(courseId)).thenReturn(0);

        Response response = courseResource.deleteCourse(courseId);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    public void testAddStudentToCourse() {
        long courseId = 1L;
        long studentId = 1L;

        doNothing().when(courseDAO).addStudentToCourse(studentId, courseId);

        Response response = courseResource.addStudentToCourse(courseId, studentId);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("Student added to course", response.getEntity());
    }

    @Test
    public void testRemoveStudentFromCourse() {
        long courseId = 1L;
        long studentId = 1L;

        doNothing().when(courseDAO).removeStudentFromCourse(studentId, courseId);

        Response response = courseResource.removeStudentFromCourse(courseId, studentId);

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetStudentsByCourseId() {
        long courseId = 1L;
        List<Student> students = Arrays.asList(new Student(), new Student());

        when(courseDAO.getStudentsByCourseId(courseId)).thenReturn(students);

        Response response = courseResource.getStudentsByCourseId(courseId);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals(students.size(), ((List<?>) response.getEntity()).size());
    }
}
