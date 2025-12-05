package com.jminiapp.examples.schoolcontrol;

public class Student {
    private String id;
    private String name;
    private int grade;

    public Student() {}

    public Student(String id, String name, int grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getGrade() { return grade; }

    public void setGrade(int grade) { this.grade = grade; }

    @Override
    public String toString() {
        return id + " - " + name + " (Grade: " + grade + ")";
    }
}