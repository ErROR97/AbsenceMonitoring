package com.example.absencemonitoring.instances;

import org.json.JSONObject;


public class Sport {
    private int id;
    private String code;
    private String type;
    private String time;
    private JSONObject date;
    private JSONObject personalIds;
    private JSONObject capacity;
    private JSONObject status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public JSONObject getPersonalIds() {
        return personalIds;
    }

    public void setPersonalIds(JSONObject personalIds) {
        this.personalIds = personalIds;
    }

    public JSONObject getCapacity() {
        return capacity;
    }

    public void setCapacity(JSONObject capacity) {
        this.capacity = capacity;
    }

    public JSONObject getStatus() {
        return status;
    }

    public void setStatus(JSONObject status) {
        this.status = status;
    }

    public JSONObject getDate() {
        return date;
    }

    public void setDate(JSONObject date) {
        this.date = date;
    }
}
