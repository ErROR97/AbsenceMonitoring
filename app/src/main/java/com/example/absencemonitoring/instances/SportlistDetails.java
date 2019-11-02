package com.example.absencemonitoring.instances;

public class SportlistDetails {
    private String name;
    private String personalId;

    public SportlistDetails(String name, String personalId) {
        this.name = name;
        this.personalId = personalId;
    }

    public String getName() {
        return name;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }
}
