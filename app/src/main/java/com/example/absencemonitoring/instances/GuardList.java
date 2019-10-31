package com.example.absencemonitoring.instances;

public class GuardList {
    private String name;
    private String type;
    private String date;
    private String startTime;
    private String amountTime;
    private String status;

    public GuardList(String name, String type, String date, String startTime, String amountTime, String status) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.amountTime = amountTime;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getAmountTime() {
        return amountTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setAmountTime(String amountTime) {
        this.amountTime = amountTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
