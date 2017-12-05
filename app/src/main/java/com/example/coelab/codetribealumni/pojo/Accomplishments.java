package com.example.coelab.codetribealumni.pojo;

import java.io.Serializable;

/**
 * Created by Yanga on 11/29/2017.
 */

public class Accomplishments implements Serializable {

    private String id;
    private String courseName;
    private String qualification;
    private String institution;
    private String year;

    public Accomplishments() {

    }

    public Accomplishments(String id, String courseName, String qualification, String institution, String year) {
        this.id = id;
        this.courseName = courseName;
        this.qualification = qualification;
        this.institution = institution;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getcourseName() {
        return courseName;
    }

    public void setcourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getqualification() {
        return qualification;
    }

    public void  setqualification(String qualification) {
        this.qualification = qualification;
    }

    public String getinstitution() {
        return institution;
    }

    public void setinstitution(String institution) {
        this.institution = institution;
    }

    public String getyear() {
        return year;
    }

    public void setyear(String year) {
        this.year = year;
    }
}
