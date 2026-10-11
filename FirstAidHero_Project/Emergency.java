package com.example.firstaidhero;

public class Emergency {
    private int id;
    private String title;
    private String dangerLevel;
    private String symptoms;

    // المُنشئ (Constructor) لاستقبال البيانات
    public Emergency(int id, String title, String dangerLevel, String symptoms) {
        this.id = id;
        this.title = title;
        this.dangerLevel = dangerLevel;
        this.symptoms = symptoms;
    }

    // دوال الاسترجاع (Getters) لقراءة البيانات
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDangerLevel() {
        return dangerLevel;
    }

    public String getSymptoms() {
        return symptoms;
    }
}