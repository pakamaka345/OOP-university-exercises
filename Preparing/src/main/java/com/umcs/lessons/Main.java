package com.umcs.lessons;

import com.umcs.lessons.exercise_1.Student;
import com.umcs.lessons.exercise_1.Subject;
import com.umcs.lessons.exercise_1.Teacher;
import com.umcs.lessons.exercise_1.Person;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        ArrayList<Student> students = new ArrayList<Student>() {{
            add(new Student("John", "Doe"));
            add(new Student("Jane", "Doe"));
        }};

        Subject subject = new Subject("Math", new Teacher("John", "Doe"), students);


        Subject subject2 = (Subject) subject.clone();
        subject2.addStudent(new Student("Alice", "Doe"));
        System.out.println(subject2.getDetails());
        System.out.println(subject.getDetails());
    }
}