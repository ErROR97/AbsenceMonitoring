package com.example.absencemonitoring.instances;

public class ControlFurlough {
    private String name;
    private String type;
    private String startDate;
    private String endDate;
    private String remainderDay;
    private String remainderHour;
    private String remainderMinute;
    private int personalId;

    public ControlFurlough(String name, String type,  String startDate, String endDate, String remainderDay, String remainderHour, String remainderMinute, int personalId) {
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remainderDay = remainderDay;
        this.remainderHour = remainderHour;
        this.remainderMinute = remainderMinute;
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

    public String getRemainderDay() {
        return remainderDay;
    }

    public String getRemainderHour() {
        return remainderHour;
    }

    public String getRemainderMinute() {
        return remainderMinute;
    }

    public int getPersonalId() {
        return personalId;
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

    public void setRemainderDay(String remainderDay) {
        this.remainderDay = remainderDay;
    }

    public void setRemainderHour(String remainderHour) {
        this.remainderHour = remainderHour;
    }

    public void setRemainderMinute(String remainderMinute) {
        this.remainderMinute = remainderMinute;
    }

    public void setPersonalId(int personalId) {
        this.personalId = personalId;
    }
}
