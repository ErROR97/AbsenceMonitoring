package com.example.absencemonitoring.instances;

public class NoticeTransport {
    private String type;
    private String date;
    private String time;
    private String address;

    public NoticeTransport(String type, String date, String time, String address) {
        this.type = type;
        this.date = date;
        this.time = time;
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
