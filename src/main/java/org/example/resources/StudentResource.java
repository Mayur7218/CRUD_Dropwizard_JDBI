package org.example.resources;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dao.StudentDAO;
import org.example.models.Course;
import org.example.models.Student;
import io.dropwizard.jersey.params.IntParam;

import java.util.List;


@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {
    private final StudentDAO studentDAO;

    public StudentResource(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @POST
    public Response createStudent(@Valid Student student) {
        studentDAO.createStudent(student);
        return Response.status(Response.Status.CREATED).entity(student).build();
    }

    @GET
    @Path("/{studentId}")
    public Response getStudent(@PathParam("studentId") IntParam studentId) {
        Student student = studentDAO.getStudentById(studentId.get());
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }
        return Response.ok(student).build();
    }

    @GET
    public Response getAllStudents() {
        List<Student> students = studentDAO.getAllStudents();
        return Response.ok(students).build();
    }

    @PUT
    @Path("/{studentId}")
    public Response updateStudent(@PathParam("studentId") IntParam studentId, @Valid Student student) {
        student.setId(studentId.get());
        studentDAO.updateStudent(student);
        return Response.ok(student).build();
    }

    @DELETE
    @Path("/{studentId}")
    public Response deleteStudent(@PathParam("studentId") IntParam studentId) {
        studentDAO.deleteStudent(studentId.get());
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    //Many to Many relation
    @POST
    @Path("/{studentId}/courses/{courseId}")
    public Response addCourseToStudent(@PathParam("studentId") long studentId, @PathParam("courseId") long courseId) {
        studentDAO.addCourseToStudent(studentId, courseId);
        return Response.status(Response.Status.CREATED).entity("Course added to student").build();
    }

    @DELETE
    @Path("/{studentId}/courses/{courseId}")
    public Response removeCourseFromStudent(@PathParam("studentId") long studentId, @PathParam("courseId") long courseId) {
        studentDAO.removeCourseFromStudent(studentId, courseId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{studentId}/courses")
    public Response getCoursesByStudentId(@PathParam("studentId") long studentId) {
        List<Course> courses = studentDAO.getCoursesByStudentId(studentId);
        return Response.ok(courses).build();
    }


}
