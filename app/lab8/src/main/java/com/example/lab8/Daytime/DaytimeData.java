package com.example.lab8.Daytime;

public class DaytimeData {
    private final int skyColor;
    private final int objectsColor;
    private final int startTime;

    public DaytimeData(int skyColor, int objectsColor, int startHour, int startMinute) {
        this.skyColor = skyColor;
        this.objectsColor = objectsColor;
        this.startTime = startHour * 60 + startMinute;
    }

    public int getObjectsColor() {
        return objectsColor;
    }

    public int getSkyColor() {
        return skyColor;
    }

    public int getStartTime() {
        return startTime;
    }
}
