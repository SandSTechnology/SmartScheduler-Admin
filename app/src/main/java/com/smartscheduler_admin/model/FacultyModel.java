package com.smartscheduler_admin.model;

public class FacultyModel {
    String ID;
    String NAME;
    String DEPARTMENT;
    String SUBJECT;

    public FacultyModel() {
    }

    public FacultyModel(String ID, String NAME, String DEPARTMENT, String SUBJECT) {
        this.ID = ID;
        this.NAME = NAME;
        this.DEPARTMENT = DEPARTMENT;
        this.SUBJECT = SUBJECT;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getDEPARTMENT() {
        return DEPARTMENT;
    }

    public void setDEPARTMENT(String DEPARTMENT) {
        this.DEPARTMENT = DEPARTMENT;
    }

    public String getSUBJECT() {
        return SUBJECT;
    }

    public void setSUBJECT(String SUBJECT) {
        this.SUBJECT = SUBJECT;
    }
}
