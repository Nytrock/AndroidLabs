package com.example.lab8.Daytime;

import android.graphics.Color;
import android.util.Log;

import com.example.lab8.ColorUtility;

public class DaytimeManager {
    private final DaytimeData[] daytimes;
    private DaytimeData previousDaytime;
    private DaytimeData nowDaytime;
    private int nextDaytimeIndex = 1;

    private float nowTime = 0;
    private float deltaTime = 0;
    private long lastTime;
    private boolean isWaitingNextDay;

    private boolean isChangingColors;
    private float changingColorsNowTime = 0;
    private int nowSkyColor;
    private int nowObjectsColor;

    public DaytimeManager() {
        daytimes = new DaytimeData[4];
        daytimes[0] = new DaytimeData(Color.parseColor("#8FDAF9"), Color.parseColor("#fff5c4"), 4, 30);
        daytimes[1] = new DaytimeData(Color.parseColor("#58ADF3"), Color.parseColor("#FFFFFF"), 10, 0);
        daytimes[2] = new DaytimeData(Color.parseColor("#A0452C"), Color.parseColor("#d3b6fc"), 18, 30);
        daytimes[3] = new DaytimeData(Color.parseColor("#2F0957"), Color.parseColor("#aeabf7"), 22, 0);

        nowDaytime = daytimes[0];
        updateColors();
        lastTime = System.currentTimeMillis();
    }

    public void update() {
        float timeSpeed = 0.1f;
        deltaTime = (System.currentTimeMillis() - lastTime) * timeSpeed;
        lastTime = System.currentTimeMillis();

        updateTime(deltaTime);
        if (isChangingColors)
            updateChangingColors(deltaTime);
    }

    private void updateTime(float deltaTime) {
        int maxTime = 24 * 60;
        nowTime += deltaTime;
        if (nowTime > maxTime){
            isWaitingNextDay = false;
            nowTime = 0;
        }

        if (nowTime >= daytimes[nextDaytimeIndex].getStartTime() && !isWaitingNextDay){
            previousDaytime = nowDaytime;
            nowDaytime = daytimes[nextDaytimeIndex];
            nextDaytimeIndex = (nextDaytimeIndex + 1) % daytimes.length;
            isChangingColors = true;

            if (nextDaytimeIndex == 0)
                isWaitingNextDay = true;
        }
    }

    private void updateChangingColors(float deltaTime) {
        changingColorsNowTime += deltaTime;
        int changingColorsTargetTime = 60;

        float interval = changingColorsNowTime / changingColorsTargetTime;
        nowSkyColor = ColorUtility.lerpColor(previousDaytime.getSkyColor(), nowDaytime.getSkyColor(), interval);
        nowObjectsColor = ColorUtility.lerpColor(previousDaytime.getObjectsColor(), nowDaytime.getObjectsColor(), interval);

        if (interval >= 1) {
            isChangingColors = false;
            changingColorsNowTime = 0;
            updateColors();
        }
    }

    private void updateColors() {
        nowSkyColor = nowDaytime.getSkyColor();
        nowObjectsColor = nowDaytime.getObjectsColor();
    }

    public int getNowSkyColor() {
        return nowSkyColor;
    }

    public int getNowObjectsColor() {
        return nowObjectsColor;
    }

    public int getStartTimeOfDaytime(int index){
        return daytimes[index].getStartTime();
    }

    public float getNowTime() {
        return nowTime;
    }

    public float getDeltaTime() {
        return deltaTime;
    }
}
