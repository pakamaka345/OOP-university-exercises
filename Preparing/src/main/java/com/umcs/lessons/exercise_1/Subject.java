package com.umcs.lessons.exercise_1;

import java.util.ArrayList;

public class Subject implements Cloneable{
    private String title;
    private Teacher teacher;
    private ArrayList<Student> students;
    public Subject(String title, Teacher teacher, ArrayList<Student> students) {
        this.title = title;
        this.teacher = teacher;
        this.students = students;
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        ArrayList<Student> clonedStudents = new ArrayList<>();
        for (Student student : students){
            clonedStudents.add(new Student(student.getName(), student.getSurname()));
        }
        return new Subject(title, new Teacher(teacher.getName(), teacher.getSurname()), clonedStudents);
    }
    public String getDetails() {
        StringBuilder sb = new StringBuilder("Teacher: " + teacher.getNameWithSurname() + "\nStudents: \n");
        for (Student student : students) {
            sb.append(student.getNameWithSurname()).append("\n");
        }
        return sb.toString();
    }
    public void addStudent(Student student){
        students.add(student);
    }
}
