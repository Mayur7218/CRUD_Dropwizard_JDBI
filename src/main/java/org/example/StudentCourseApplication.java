package org.example;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;
import org.example.resources.StudentResource;
import org.example.resources.CourseResource;
import org.example.dao.StudentDAO;
import org.example.dao.CourseDAO;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;


public class StudentCourseApplication extends Application<StudentCourseConfiguration> {

    public static void main(String[] args) throws Exception {
        new StudentCourseApplication().run(args);
    }

    @Override
    public void run(StudentCourseConfiguration configuration, Environment environment) {
        Jdbi jdbi = Jdbi.create(configuration.getJdbcUrl(), configuration.getUsername(), configuration.getPassword());

        jdbi.installPlugin(new SqlObjectPlugin());

         StudentDAO studentDAO = jdbi.onDemand(StudentDAO.class);
         CourseDAO courseDAO = jdbi.onDemand(CourseDAO.class);

        environment.jersey().register(new StudentResource(studentDAO));
        environment.jersey().register(new CourseResource(courseDAO));
    }
}
