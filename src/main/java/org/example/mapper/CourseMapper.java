//package org.example.mapper;
//
//
//import org.example.models.Course;
//import org.jdbi.v3.core.mapper.RowMapper;
//import org.jdbi.v3.core.statement.StatementContext;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class CourseMapper implements RowMapper<Course> {
//    @Override
//    public Course map(ResultSet rs, StatementContext ctx) throws SQLException {
//        Course course = new Course();
//        course.setId(rs.getLong("id"));
//        course.setCourseName(rs.getString("course_name"));
//        course.setDuration(rs.getInt("duration"));
//        return course;
//    }
//}


/* ----> WE CAN USE THIS MAPPER TO CONVERT THE DATA TO REQUIRED FORMAT WHICH IS BEING SENT TO CLIENT <----*/