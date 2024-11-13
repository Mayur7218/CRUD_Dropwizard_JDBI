//package org.example.mapper;
//
//import org.example.models.Student;
//import org.jdbi.v3.core.mapper.RowMapper;
//import org.jdbi.v3.core.statement.StatementContext;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class StudentMapper implements RowMapper<Student> {
//    @Override
//    public Student map(ResultSet rs, StatementContext ctx) throws SQLException {
//        Student student = new Student();
//        student.setId(rs.getLong("id"));
//        student.setName(rs.getString("name"));
//        student.setAge(rs.getInt("age"));
//        student.setGrade(rs.getDouble("grade"));
//        return student;
//    }
//}
//

/* ----> WE CAN USE THIS MAPPER TO CONVERT THE DATA TO REQUIRED FORMAT WHICH IS BEING SENT TO CLIENT <----*/
