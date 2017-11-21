package com.example.coelab.codetribealumni.data;

/**
 * Created by Laser on 11/14/2017.
 */

public class Project {
    private String projectName;
    private String projectLink;

    public Project() {

    }

    public Project(String projectName, String projectLink) {
        this.projectName = projectName;
        this.projectLink = projectLink;
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

    @Override
    public String toString() {
        return  projectName + '\n' +
                projectLink;
    }
}
