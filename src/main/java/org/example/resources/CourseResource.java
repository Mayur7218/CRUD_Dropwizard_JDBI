package org.example.resources;


import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dao.CourseDAO;
import org.example.models.Course;
import io.dropwizard.jersey.params.IntParam;
import org.example.models.Student;

import java.util.List;


@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {
    private final CourseDAO courseDAO;

    public CourseResource(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @POST
    public Response createCourse(@Valid Course course) {
        courseDAO.createCourse(course);
        return Response.status(Response.Status.CREATED).entity(course).build();
    }

    @GET
    @Path("/{courseId}")
    public Response getCourse(@PathParam("courseId") IntParam courseId) {
        Course course = courseDAO.getCourseById(courseId.get());
        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Course not found").build();
        }
        return Response.ok(course).build();
    }

    @GET
    public Response getAllCourses() {
        List<Course> courses = courseDAO.getAllCourses();
        if (courses.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(courses).build();
    }


    @PUT
    @Path("/{courseId}")
    public Response updateCourse(@PathParam("courseId") IntParam courseId, @Valid Course course) {
        course.setId(courseId.get());
        courseDAO.updateCourse(course);
        return Response.ok(course).build();
    }

    @DELETE
    @Path("/{courseId}")
    public Response deleteCourse(@PathParam("courseId") IntParam courseId) {
        courseDAO.deleteCourse(courseId.get());
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    //Many to Many relation

    @POST
    @Path("/{courseId}/students/{studentId}")
    public Response addStudentToCourse(@PathParam("courseId") long courseId, @PathParam("studentId") long studentId) {
        courseDAO.addStudentToCourse(studentId, courseId);
        return Response.status(Response.Status.CREATED).entity("Student added to course").build();
    }

    @DELETE
    @Path("/{courseId}/students/{studentId}")
    public Response removeStudentFromCourse(@PathParam("courseId") long courseId, @PathParam("studentId") long studentId) {
        courseDAO.removeStudentFromCourse(studentId, courseId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{courseId}/students")
    public Response getStudentsByCourseId(@PathParam("courseId") long courseId) {
        List<Student> students = courseDAO.getStudentsByCourseId(courseId);
        return Response.ok(students).build();
    }
}
