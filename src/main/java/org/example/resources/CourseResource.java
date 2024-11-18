package org.example.resources;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dao.CourseDAO;
import org.example.models.Course;
import org.example.models.Student;
import java.util.List;


@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {
    private final CourseDAO courseDAO;

    public CourseResource(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }


    @GET
    @Path("/{courseId}")
    public Response getCourse(@PathParam("courseId") long courseId) {
        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Course not found").build();
        }
        return Response.ok(course).build();
    }

    @GET
    public Response getAllCourses(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @QueryParam("courseName") String courseName,
            @QueryParam("sortBy") @DefaultValue("id") String sortBy,
            @QueryParam("sortOrder") @DefaultValue("asc") String sortOrder) {

        int limit = size;
        int offset = (page-1)*size;

        List<Course> courses = courseDAO.getCoursesWithPaginationAndFilters(courseName, sortBy, sortOrder, limit, offset);

        if (courses.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(courses).build();
    }

    @POST
    public Response createCourse(@Valid Course course) {
        int result = courseDAO.createCourse(course);
        if (result > 0) {
            return Response.status(Response.Status.CREATED).entity(course).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to create course").build();
    }

    @PUT
    @Path("/{courseId}")
    public Response updateCourse(@PathParam("courseId") long courseId, @Valid Course course) {
        course.setId(courseId);
        int result = courseDAO.updateCourse(course);
        if (result > 0) {
            return Response.ok(course).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to update course").build();
    }

    @DELETE
    @Path("/{courseId}")
    public Response deleteCourse(@PathParam("courseId") long courseId) {
        int result = courseDAO.deleteCourse(courseId);
        if (result > 0) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to delete course").build();
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
