package com.example.lab8.Drawer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.lab8.ColorUtility;
import com.example.lab8.Daytime.DaytimeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CloudsDrawer extends BaseDrawer {
    private final Random random;
    private ArrayList<CloudDrawer> clouds;
    private int cloudsColor = Color.parseColor("#eaeaea");

    public CloudsDrawer(Paint paint) {
        super(paint);
        random = new Random();
        clouds = new ArrayList<>();

        spawnStartClouds();
    }

    @Override
    public void draw(Canvas canvas, DaytimeManager daytimeManager) {
        updateExistingClouds(canvas, daytimeManager);
        trySpawnNewCloud();
    }

    private void trySpawnNewCloud() {
        int randomNumber = random.nextInt(75);
        if (randomNumber != 42)
            return;

        spawnNewCloud();
    }

    private void spawnNewCloud() {
        int width = random.nextInt(400) + 100;
        int x = -width / 2;
        spawnCloud(x, width);
    }

    private void updateExistingClouds(Canvas canvas, DaytimeManager daytimeManager) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ColorUtility.multiplyColors(cloudsColor, daytimeManager.getNowObjectsColor()));

        for (int i = 0; i < clouds.size(); i++) {
            CloudDrawer cloud = clouds.get(i);
            cloud.draw(canvas, daytimeManager);
            if (cloud.getPosition().getX() >= 2360 + cloud.getWidth() / 2f){
                destroyCloud(i);
                i--;
            }
        }
    }

    private void destroyCloud(int index) {
        clouds.remove(index);
    }

    private void spawnCloud(int x, int width) {
        int y = random.nextInt(400) + 50;
        int height = random.nextInt(15) + 40;

        Point position = new Point(x, y);
        CloudDrawer cloud = new CloudDrawer(paint, position, width, height, random);
        clouds.add(cloud);
    }

    private void spawnStartClouds() {
        int startCloudsCount = 10;
        for (int i = 0; i < startCloudsCount; i++){
            int x = random.nextInt(2360);
            int width = random.nextInt(400) + 100;
            spawnCloud(x, width);
        }
    }
}
