package com.example.absencemonitoring.instances;

public class SportmanTiming {
    private String time;
    private String startTime;
    private String endTime;

    public SportmanTiming(String time, String startTime, String endTime) {
        this.time = time;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTime() {
        return time;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
