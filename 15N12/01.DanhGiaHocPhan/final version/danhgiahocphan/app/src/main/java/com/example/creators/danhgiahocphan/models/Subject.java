package com.example.creators.danhgiahocphan.models;

import java.io.Serializable;

/**
 * Created by vanch on 9/14/2017.
 */

public class Subject implements Serializable {
    private String id;
    private String name;
    private String part;


    public Subject(String id, String name, String part) {
        this.id = id;
        this.name = name;
        this.part = part;
    }

    public Subject() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    @Override
    public String toString() {
        return "Subject{" +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", part='" + part + '\'' +
                '}';
    }
}
