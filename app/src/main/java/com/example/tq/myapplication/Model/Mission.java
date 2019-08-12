package com.example.tq.myapplication.Model;

import java.util.Date;

public class Mission {
    public static final String TYPE_A = "警方提取";
    public static final String TYPE_B = "警方提讯";
    public static final String TYPE_C = "法院提取";
    public static final String TYPE_D = "家属会见";

    public static final String STATUS_A = "NEW";
    public static final String STATUS_B = "ISDOING";
    public static final String STATUS_C = "FINISHED";


    private String type;
    private String info;
    private String status;
    private Prisoner prisoner;
    private Date start_time;
    private Date end_time;

    public Mission(){}

    public Mission(String type, String info, String status,Prisoner prisoner) {
        this.type = type;
        this.info = info;
        this.status = status;
        this.prisoner = prisoner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Prisoner getPrisoner() {
        return prisoner;
    }

    public void setPrisoner(Prisoner prisoner) {
        this.prisoner = prisoner;
    }
}
