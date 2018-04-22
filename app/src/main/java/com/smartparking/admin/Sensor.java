package com.smartparking.admin;

import java.io.Serializable;

/**
 * Created by ADMIN on 3/19/2018.
 */

public class Sensor implements Serializable {
    private int id;
    private String name;
    private String status;
    private String username;

    public Sensor() {
    }

    public Sensor(int id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Sensor(int id, String name, String status, String username) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
