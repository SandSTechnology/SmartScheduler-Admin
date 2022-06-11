package com.smartscheduler_admin.model;

public class DepartmentsModel {
    String ID;
    String DEPARTMENT;


    public DepartmentsModel() {
    }

    public DepartmentsModel(String ID, String DEPARTMENT) {
        this.ID = ID;

        this.DEPARTMENT = DEPARTMENT;

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDEPARTMENT() {
        return DEPARTMENT;
    }

    public void setDEPARTMENT(String DEPARTMENT) {
        this.DEPARTMENT = DEPARTMENT;
    }
}
