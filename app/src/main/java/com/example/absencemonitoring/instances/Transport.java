package com.example.absencemonitoring.instances;

public class Transport {
    private int id;
    private String name;
    private  String personalId;
    private String date;
    private String shift;
    private String address;

    public Transport() {
    }

    public Transport(String name, String personalId, String date, String shift, String address) {
        this.name = name;
        this.personalId = personalId;
        this.date = date;
        this.shift = shift;
        this.address = address;
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

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
