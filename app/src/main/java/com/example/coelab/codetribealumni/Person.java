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

    private boolean disability;
    private String countryOfBirth;
    private String id;
    private int age;
    private String qualification;
    private String qualificationDescription;
    private String institution;
    private boolean employementStatus;
    private String companyName;
    private String duties;
    private String startDate;
    private String endDate;
    private String qualifYearObtained;


    public Person(boolean disability, String countryOfBirth, String id, int age, String qualification, String qualificationDescription, String institution, boolean employementStatus, String companyName, String duties, String startDate, String endDate, String qualifYearObtained)
    {
        this.disability = disability;
        this.countryOfBirth = countryOfBirth;
        this.id = id;
        this.age = age;
        this.qualification = qualification;
        this.qualificationDescription = qualificationDescription;
        this.institution = institution;
        this.employementStatus = employementStatus;
        this.companyName = companyName;
        this.duties = duties;
        this.startDate = startDate;
        this.endDate = endDate;
        this.qualifYearObtained = qualifYearObtained;
    }

    public Person() {
    }

    public Person(String id,String name, String surname, String cell, String gender, String email, String role, String location, String year) {
        this.id = id;
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

    public void setDisability(boolean disability)
    {
        this.disability = disability;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setQualificationDescription(String qualificationDescription) {
        this.qualificationDescription = qualificationDescription;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setEmployementStatus(boolean employementStatus) {
        this.employementStatus = employementStatus;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setDuties(String duties) {
        this.duties = duties;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setQualifYearObtained(String qualifYearObtained) {
        this.qualifYearObtained = qualifYearObtained;
    }

    public boolean isDisability() {
        return disability;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public String getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getQualification() {
        return qualification;
    }

    public String getQualificationDescription() {
        return qualificationDescription;
    }

    public String getInstitution() {
        return institution;
    }

    public boolean isEmployementStatus() {
        return employementStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDuties() {
        return duties;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getQualifYearObtained()
    {
        return qualifYearObtained;
    }
}
