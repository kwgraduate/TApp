package com.example.tapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    //@ColumnInfo(name = "order_id")
    private int id=0; // 하나의 user에 대한 고유 ID의 값

    private String name;

    private String age;

    private String sex;

    //@ColumnInfo(name = "order_temperature1")
    private String temperature1;

    //@ColumnInfo(name = "order_temperature2")
    private String temperature2;

    //@ColumnInfo(name = "order_temperature3")
    private String temperature3;

    //@ColumnInfo(name = "order_temperature4")
    private String temperature4;

    //@ColumnInfo(name = "order_count")
    private String count;

    private String hour;

    private String minute;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTemperature1() {
        return temperature1;
    }

    public void setTemperature1(String temperature1) {
        this.temperature1 = temperature1;
    }

    public String getTemperature2() {
        return temperature2;
    }

    public void setTemperature2(String temperature2) {
        this.temperature2 = temperature2;
    }

    public String getTemperature3() {
        return temperature3;
    }

    public void setTemperature3(String temperature3) {
        this.temperature3 = temperature3;
    }

    public String getTemperature4() {
        return temperature4;
    }

    public void setTemperature4(String temperature4) {
        this.temperature4 = temperature4;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

}
