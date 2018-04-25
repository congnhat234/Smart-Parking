package com.smartparking.admin;

import java.io.Serializable;

/**
 * Created by ADMIN on 3/19/2018.
 */

public class Booked implements Serializable  {
    private String biensoxe;
    private String giovao;
    private String loaixe;
    private String username;
    private String vitri;

    public Booked() {
    }

    public Booked(String biensoxe, String giovao, String loaixe, String username, String vitri) {
        this.biensoxe = biensoxe;
        this.giovao = giovao;
        this.loaixe = loaixe;
        this.username = username;
        this.vitri = vitri;
    }

    public String getBiensoxe() {
        return biensoxe;
    }

    public void setBiensoxe(String biensoxe) {
        this.biensoxe = biensoxe;
    }

    public String getGiovao() {
        return giovao;
    }

    public void setGiovao(String giovao) {
        this.giovao = giovao;
    }

    public String getLoaixe() {
        return loaixe;
    }

    public void setLoaixe(String loaixe) {
        this.loaixe = loaixe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVitri() {
        return vitri;
    }

    public void setVitri(String vitri) {
        this.vitri = vitri;
    }
}
