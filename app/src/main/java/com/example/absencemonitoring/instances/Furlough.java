package com.example.absencemonitoring.instances;

public class Furlough {

    private String personalIdemployee;
    private int id;
    private String name;
    private String currentDate;
    private String personalIdMaster;
    private String leaveType;
    private String startTime;
    private String timeLeave;
    private String startDate;
    private String descriptionLeave;
    private String statusArchive;
    private String statusLeave;
    private int started;

    public String getPersonalIdemployee() {
        return personalIdemployee;
    }

    public void setPersonalIdemployee(String personalIdemployee) {
        this.personalIdemployee = personalIdemployee;
    }

    public String getPersonalIdMaster() {
        return personalIdMaster;
    }

    public void setPersonalIdMaster(String personalIdMaster) {
        this.personalIdMaster = personalIdMaster;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTimeLeave() {
        return timeLeave;
    }

    public void setTimeLeave(String timeLeave) {
        this.timeLeave = timeLeave;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDescriptionLeave() {
        return descriptionLeave;
    }

    public void setDescriptionLeave(String descriptionLeave) {
        this.descriptionLeave = descriptionLeave;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getStatusArchive() {
        return statusArchive;
    }

    public void setStatusArchive(String statusArchive) {
        this.statusArchive = statusArchive;
    }

    public String getStatusLeave() {
        return statusLeave;
    }

    public void setStatusLeave(String statusLeave) {
        this.statusLeave = statusLeave;
    }

    public int getStarted() {
        return started;
    }

    public void setStarted(int isStarted) {
        this.started = isStarted;
    }
}
