package com.example.coelab.codetribealumni.pojo;

import java.io.Serializable;

/**
 * Created by Laser on 11/16/2017.
 */

public class Experience implements Serializable {
    private String id;
    private String companyName;
    private String position;
    private String startSate;
    private String endDate;

    public Experience() {

    }

    public Experience(String id, String companyName, String position, String startSate, String endDate) {
        this.id = id;
        this.companyName = companyName;
        this.position = position;
        this.startSate = startSate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStartSate() {
        return startSate;
    }

    public void setStartSate(String startSate) {
        this.startSate = startSate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
