package com.example.tq.myapplication.Model;

import java.io.Serializable;

public class Prisoner implements Serializable {
    private String name;
    private long prisonid;
    private long prisonRoomId;

    public Prisoner(){
    }

    public Prisoner(String name, long prisonid, long prisonRoomId) {
        this.name = name;
        this.prisonid = prisonid;
        this.prisonRoomId = prisonRoomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrisonid() {
        return prisonid;
    }

    public void setPrisonid(long prisonid) {
        this.prisonid = prisonid;
    }

    public long getPrisonRoomId() {
        return prisonRoomId;
    }

    public void setPrisonRoomId(long prisonRoomId) {
        this.prisonRoomId = prisonRoomId;
    }

    @Override
    public String toString() {
        return "Prisoner{" +
                "name='" + name + '\'' +
                ", prisonid=" + prisonid +
                ", prisonRoomId=" + prisonRoomId +
                '}';
    }
}
