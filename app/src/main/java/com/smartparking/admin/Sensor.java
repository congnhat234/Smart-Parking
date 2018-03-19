package com.smartparking.admin;

/**
 * Created by ADMIN on 3/19/2018.
 */

public class Sensor {
    private String id;
    private String name;
    private int status;

    public Sensor() {
    }

    public Sensor(String id, String name, int status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
