package com.example.coelab.codetribealumni.pojo;

/**
 * Created by Laser on 11/14/2017.
 */

public class Project {
    private String id;
    private String projectName;
    private String projectLink;

    public Project() {

    }

    public Project(String id,String projectName, String projectLink) {
        this.projectName = projectName;
        this.projectLink = projectLink;
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  projectName + '\n' +
                projectLink;
    }
}
