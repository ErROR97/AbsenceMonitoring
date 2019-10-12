package com.example.absencemonitoring;

public class Employee {
    private String name;
    private String position;
    private boolean status;

    public Employee(String name, String position, boolean status) {
        this.name = name;
        this.position = position;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public boolean isStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
