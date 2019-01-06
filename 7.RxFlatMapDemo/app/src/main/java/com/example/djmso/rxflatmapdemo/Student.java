package com.example.djmso.rxflatmapdemo;

public class Student {
    private String name;
    private String email;
    private int age;
    private String registrationDate;

    public Student(String name, String email, int age, String registrationDate){
        this.name = name;
        this.email = email;
        this.age = age;
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }
}
