package com.example.absencemonitoring.instances;

public class Furlough {
    private String name;
    private String type;
    private String startDate;
    private String endDate;
    private String amountTime;

    public Furlough(String name, String type, String startDate, String endDate, String amountTime) {
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amountTime = amountTime;
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getAmountTime() {
        return amountTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setAmountTime(String amountTime) {
        this.amountTime = amountTime;
    }
}
