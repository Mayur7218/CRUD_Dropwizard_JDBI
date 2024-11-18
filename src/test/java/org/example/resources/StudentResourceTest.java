package org.example.resources;

import jakarta.ws.rs.core.Response;
import org.example.dao.StudentDAO;
import org.example.models.Course;
import org.example.models.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StudentResourceTest {

    private StudentDAO studentDAO;
    private StudentResource studentResource;

    @BeforeEach
    public void setUp() {
        studentDAO = Mockito.mock(StudentDAO.class);
        studentResource = new StudentResource(studentDAO);
    }

    @Test
    public void testGetStudent() {
        long studentId = 1;
        Student student = new Student();
        student.setId(studentId);
        student.setName("Mayur");
        student.setAge(20);
        student.setGrade(90.0);

        when(studentDAO.getStudentById(studentId)).thenReturn(student);

        Response response = studentResource.getStudent(studentId);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        assertTrue(response.getEntity() instanceof Student);
        Student returnedStudent = (Student) response.getEntity();
        assertEquals("Mayur", returnedStudent.getName());
    }

    @Test
    public void testGetStudentNotFound() {
        long studentId = 1;

        when(studentDAO.getStudentById(studentId)).thenReturn(null);

        Response response = studentResource.getStudent(studentId);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student();
        student.setName("Rutik");
        student.setAge(22);
        student.setGrade(85.0);

        when(studentDAO.createStudent(student)).thenReturn(1);

        Response response = studentResource.createStudent(student);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    public void testCreateStudentFailed() {
        Student student = new Student();
        student.setName("Rutik");
        student.setAge(22);
        student.setGrade(85.0);

        when(studentDAO.createStudent(student)).thenReturn(0);

        Response response = studentResource.createStudent(student);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateStudent() {
        long studentId = 1;
        Student student = new Student();
        student.setId(studentId);
        student.setName("John Smith");
        student.setAge(23);
        student.setGrade(88.0);

        when(studentDAO.updateStudent(student)).thenReturn(1);

        Response response = studentResource.updateStudent(studentId, student);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    public void testUpdateStudentNotFound() {
        long studentId = 1;
        Student student = new Student();
        student.setId(studentId);
        student.setName("John Smith");
        student.setAge(23);
        student.setGrade(88.0);

        when(studentDAO.updateStudent(student)).thenReturn(0);

        Response response = studentResource.updateStudent(studentId, student);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeleteStudent() {
        long studentId = 1;

        when(studentDAO.deleteStudent(studentId)).thenReturn(1);

        Response response = studentResource.deleteStudent(studentId);

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeleteStudentNotFound() {
        long studentId = 1;

        when(studentDAO.deleteStudent(studentId)).thenReturn(0);

        Response response = studentResource.deleteStudent(studentId);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetAllStudents() {
        Student student1 = new Student();
        student1.setId(1);
        student1.setName("Mayur");
        student1.setAge(20);
        student1.setGrade(90.0);

        Student student2 = new Student();
        student2.setId(2);
        student2.setName("Rutik");
        student2.setAge(22);
        student2.setGrade(85.0);

        List<Student> students = Arrays.asList(student1, student2);

        when(studentDAO.getFilteredSortedPaginatedStudents(any(), anyInt(), anyInt(), anyDouble(), anyDouble(), anyInt(), anyInt()))
                .thenReturn(students);

        Response response = studentResource.getAllStudents(null, null, null, null, null, 1, 10);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        List<Student> returnedStudents = (List<Student>) response.getEntity();
        assertEquals(2, returnedStudents.size());
    }

    @Test
    public void testAddCourseToStudent() {
        long studentId = 1;
        long courseId = 101;

        doNothing().when(studentDAO).addCourseToStudent(studentId, courseId);

        Response response = studentResource.addCourseToStudent(studentId, courseId);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testRemoveCourseFromStudent() {
        long studentId = 1;
        long courseId = 101;

        doNothing().when(studentDAO).removeCourseFromStudent(studentId, courseId);

        Response response = studentResource.removeCourseFromStudent(studentId, courseId);

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetCoursesByStudentId() {
        long studentId = 1;

        Course course1 = new Course();
        course1.setId(101);
        course1.setCourseName("Math 101");

        Course course2 = new Course();
        course2.setId(102);
        course2.setCourseName("Science 101");

        List<Course> courses = Arrays.asList(course1, course2);

        when(studentDAO.getCoursesByStudentId(studentId)).thenReturn(courses);

        Response response = studentResource.getCoursesByStudentId(studentId);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        List<Course> returnedCourses = (List<Course>) response.getEntity();
        assertEquals(2, returnedCourses.size());
    }
}
