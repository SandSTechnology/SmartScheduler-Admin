package com.smartscheduler_admin.model;

public class RoomsModel {
    String ID;
    String ROOM;


    public RoomsModel() {
    }

    public RoomsModel(String ID, String ROOM) {
        this.ID = ID;

        this.ROOM = ROOM;

    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    public String getROOM() {
        return ROOM;
    }

    public void setROOM(String ROOM) {
        this.ROOM = ROOM;
    }

}
