package org.example.resources;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dao.StudentDAO;
import org.example.models.Course;
import org.example.models.Student;
import java.util.List;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {
    private final StudentDAO studentDAO;

    public StudentResource(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }


    @GET
    @Path("/{studentId}")
    public Response getStudent(@PathParam("studentId") long studentId) {
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }
        return Response.ok(student).build();
    }

    @POST
    public Response createStudent(@Valid Student student) {
        int rowsAffected = studentDAO.createStudent(student);
        if (rowsAffected > 0) {
            return Response.status(Response.Status.CREATED).entity(student).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to create student").build();
        }
    }

    @PUT
    @Path("/{studentId}")
    public Response updateStudent(@PathParam("studentId") long studentId, @Valid Student student) {
        student.setId(studentId);
        int rowsAffected = studentDAO.updateStudent(student);
        if (rowsAffected > 0) {
            return Response.ok(student).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }
    }

    @DELETE
    @Path("/{studentId}")
    public Response deleteStudent(@PathParam("studentId") long studentId) {
        int rowsAffected = studentDAO.deleteStudent(studentId);
        if (rowsAffected > 0) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }
    }
    //Sorting, Filtering, Pagination
    @GET
    public Response getAllStudents(
            @QueryParam("sort") String sort,
            @QueryParam("minAge") Integer minAge,
            @QueryParam("maxAge") Integer maxAge,
            @QueryParam("minGrade") Double minGrade,
            @QueryParam("maxGrade") Double maxGrade,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("10") int size) {

        int offset = (page - 1) * size;
        int limit = size;

        Integer ageMin = minAge != null ? minAge : 0;
        Integer ageMax = maxAge != null ? maxAge : Integer.MAX_VALUE;
        Double gradeMin = minGrade != null ? minGrade : 0.0;
        Double gradeMax = maxGrade != null ? maxGrade : Double.MAX_VALUE;


        List<Student> students = studentDAO.getFilteredSortedPaginatedStudents(sort, ageMin, ageMax, gradeMin, gradeMax, limit, offset);

        return Response.ok(students).build();
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
