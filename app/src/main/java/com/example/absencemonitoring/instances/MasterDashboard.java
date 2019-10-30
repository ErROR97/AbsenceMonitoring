package com.example.absencemonitoring.instances;

public class MasterDashboard {
    private String type;
    private String name;
    private String date;
    private String time;
    private String personalCode;

    public MasterDashboard(String type, String name, String date, String time, String personalCode) {
        this.type = type;
        this.name = name;
        this.date = date;
        this.time = time;
        this.personalCode = personalCode;
    }

    public String getName() {
        return name;
    }

    public String getType() { return type; }

    public String getDate() { return date; }

    public String getTime() { return time; }

    public String getPersonalCode() { return personalCode; }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) { this.type = type; }

    public void setDate(String date) { this.date = date; }

    public void setTime(String time) { this.time = time; }

    public void setPersonalCode(String personalCode) { this.personalCode = personalCode; }
}
