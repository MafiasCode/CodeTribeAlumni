package com.example.coelab.codetribealumni;

import java.io.Serializable;

/**
 * Created by Codetribe on 11/7/2017.
 */

public class Person implements Serializable
{
    private String name;
    private String surname;
    private String cell;
    private String gender;
    private String email;
    private String role;
    private String location;
    private String year;

    public Person() {
    }

    public Person(String name, String surname, String cell, String gender, String email, String role, String location, String year) {
        this.name = name;
        this.surname = surname;
        this.cell = cell;
        this.gender = gender;
        this.email = email;
        this.role = role;
        this.location = location;
        this.year = year;
    }
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public String getCell() {
        return cell;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }public String getLocation() {
    return location;
}
    public String getYear() {
        return year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", cell='" + cell + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", location='" + location + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
