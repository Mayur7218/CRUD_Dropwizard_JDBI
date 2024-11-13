package org.example.models;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private long id;
    private String courseName;
    private int duration;

    List<Student> students=new ArrayList<>();

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student){
        this.students.add(student);
        student.getCourses().add(this);
    }
    public void removeStudent(Student student){
        this.students.remove(student);
        student.getCourses().remove(this);
    }
}
