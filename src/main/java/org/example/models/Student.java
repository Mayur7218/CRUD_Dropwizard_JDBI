package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private long id;
    private String name;
    private int age;
    private double grade;

    List<Course> courses = new ArrayList<>();

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getGrade() { return grade; }
    public void setGrade(double grade) { this.grade = grade; }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course){
        this.courses.add(course);
        course.getStudents().add(this);
    }
    public void removeCourse(Course course){
        this.courses.remove(course);
        course.getStudents().remove(this);

    }
}
