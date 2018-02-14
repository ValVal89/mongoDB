package org.mongoDB.org.mongoDB.pojos;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class Student
{
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private ArrayList<Hobby> hobby;

    public Student() {
    }

    public Student(Long id,  String firstName, String lastName, int age, ArrayList<Hobby> hobby) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.hobby = hobby;
    }

    public Student(String firstName, String lastName, int age, ArrayList<Hobby> hobby) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.hobby = hobby;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList<Hobby> getHobby() {
        return hobby;
    }

    public void setHobby(ArrayList<Hobby> hobby) {
        this.hobby = hobby;
    }
}
