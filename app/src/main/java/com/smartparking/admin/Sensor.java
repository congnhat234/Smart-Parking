package com.smartparking.admin;

/**
 * Created by ADMIN on 3/19/2018.
 */

public class Sensor {
    private int id;
    private String name;
    private int status;
    private String username;

    public Sensor() {
    }

    public Sensor(int id, String name, int status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Sensor(int id, String name, int status, String username) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
