package com.example.absencemonitoring.instances;

public class FurloughArchive {
    private int id;
    private String fullName;
    private String personalIdEmployee;
    private String personalIdMaster;
    private String leaveType;
    private String startTime;
    private String timeLeave;
    private String startDate;
    private String description;
    private String descriptionLeave;
    private String currentDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersonalIdEmployee() {
        return personalIdEmployee;
    }

    public void setPersonalIdEmployee(String personalIdEmployee) {
        this.personalIdEmployee = personalIdEmployee;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionLeave() {
        return descriptionLeave;
    }

    public void setDescriptionLeave(String descriptionLeave) {
        this.descriptionLeave = descriptionLeave;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
