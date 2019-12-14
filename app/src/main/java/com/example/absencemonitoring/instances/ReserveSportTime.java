package com.example.absencemonitoring.instances;

public class ReserveSportTime {
    private String checkText;
    private int checkImage;

    public ReserveSportTime(String checkText, int checkImage) {
        this.checkText = checkText;
        this.checkImage = checkImage;
    }

    public String getCheckText() {
        return checkText;
    }

    public void setCheckText(String checkText) {
        this.checkText = checkText;
    }

    public int getCheckImage() {
        return checkImage;
    }

    public void setCheckImage(int checkImage) {
        this.checkImage = checkImage;
    }
}
