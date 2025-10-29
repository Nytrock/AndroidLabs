package com.example.lab8.Drawer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import com.example.lab8.ColorUtility;
import com.example.lab8.Daytime.DaytimeManager;

public class ForegroundDrawer extends BaseDrawer {
    private Canvas canvas;
    private int lightColor;

    private final Path houseBasePath;
    private final Path houseRoofPath;
    private final Path treePath;
    private final Path mountainPath;
    private final Path mountainSnowPath1;
    private final Path mountainSnowPath2;

    private final int hillsColor = Color.parseColor("#82c341");
    private final int treesColor = Color.parseColor("#007f3c");
    private final int darkTreesColor = Color.parseColor("#006931");
    private final int houseBaseColor = Color.parseColor("#f0c351");
    private final int houseDetailsColor = Color.parseColor("#6d3e08");
    private final int mountainColor = Color.parseColor("#4f3d1b");
    private final int mountainSnowColor = Color.parseColor("#cbcbcb");

    public ForegroundDrawer(Paint paint) {
        super(paint);

        houseBasePath = new Path();
        houseBasePath.moveTo(1832, 720);
        houseBasePath.lineTo(1867, 758);
        houseBasePath.lineTo(1973, 758);
        houseBasePath.lineTo(1973, 895);
        houseBasePath.lineTo(1795, 895);
        houseBasePath.lineTo(1795, 757);

        houseRoofPath = new Path();
        houseRoofPath.moveTo(1832, 720);
        houseRoofPath.lineTo(1939, 720);
        houseRoofPath.lineTo(1973, 758);
        houseRoofPath.lineTo(1867, 758);

        treePath = new Path();
        treePath.moveTo(0, 0);
        treePath.lineTo(-57, 0);
        treePath.lineTo(-42, -38);
        treePath.lineTo(-47, -38);
        treePath.lineTo(-37, -76);
        treePath.lineTo(-43, -76);
        treePath.lineTo(0, -207);
        treePath.lineTo(43, -76);
        treePath.lineTo(37, -76);
        treePath.lineTo(47, -38);
        treePath.lineTo(42, -38);
        treePath.lineTo(57, 0);

        mountainPath = new Path();
        mountainPath.moveTo(0, 0);
        mountainPath.lineTo(-700, 0);
        mountainPath.lineTo(0, -795);
        mountainPath.lineTo(700, 0);

        mountainSnowPath1 = new Path();
        mountainSnowPath1.moveTo(0, -795);
        mountainSnowPath1.lineTo(-268, -495);
        mountainSnowPath1.lineTo(-195, -505);
        mountainSnowPath1.lineTo(-130, -475);
        mountainSnowPath1.lineTo(-58, -511);
        mountainSnowPath1.lineTo(272, -487);

        mountainSnowPath2 = new Path();
        mountainSnowPath2.moveTo(0, -795);
        mountainSnowPath2.lineTo(-268, -495);
        mountainSnowPath2.lineTo(-176, -520);
        mountainSnowPath2.lineTo(16, -427);
        mountainSnowPath2.lineTo(91, -557);
        mountainSnowPath2.lineTo(272, -487);
    }

    @Override
    public void draw(Canvas canvas, DaytimeManager daytimeManager) {
        this.canvas = canvas;
        lightColor = daytimeManager.getNowObjectsColor();

        drawMountains();
        drawHills();
        drawTrees();
        drawHouse();
    }

    private void drawHills() {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ColorUtility.multiplyColors(hillsColor, lightColor));

        canvas.drawOval(-500, 591, -500 + 993, 591 + 974, paint);
        canvas.drawOval(410, 647, 410 + 1017, 647 + 731, paint);
        canvas.drawOval(884, 821, 2025 + 884, 821 + 573, paint);
    }

    private void drawTrees() {
        paint.setStyle(Paint.Style.FILL);

        paint.setColor(ColorUtility.multiplyColors(treesColor, lightColor));
        drawTree(1234, 811, 1);
        drawTree(1116, 765, 1);
        drawTree(529, 875, 1);
        drawTree(430, 900, 1);
        drawTree(1053, 700, 0.67f);
        drawTree(947, 677, 0.75f);
        drawTree(807, 701, 0.75f);
        drawTree(190, 1006, 1.86f);
        drawTree(627, 978, 1.2f);
        drawTree(1711, 960, 1.38f);
        drawTree(2143, 874, 1.3f);
        drawTree(1882, 891, 1.37f);

        paint.setColor(ColorUtility.multiplyColors(darkTreesColor, lightColor));
        drawTree(540, 1030, 0.9f);
        drawTree(76, 1005, 0.9f);
        drawTree(333, 1121, 2.3f);
        drawTree(2034, 885, 2.07f);
    }

    private void drawTree(int x, int y, float scale) {
        canvas.save();
        canvas.translate(x, y);
        canvas.scale(scale, scale);
        canvas.drawPath(treePath, paint);
        canvas.restore();
    }

    private void drawMountains() {
        paint.setStyle(Paint.Style.FILL);
        int mountain = ColorUtility.multiplyColors(mountainColor, lightColor);
        int mountainSnow = ColorUtility.multiplyColors(mountainSnowColor, lightColor);

        drawMountain(507, 980, 0.9f, mountainSnowPath2, mountain, mountainSnow);
        drawMountain(1150, 880, 1, mountainSnowPath1, mountain, mountainSnow);
        drawMountain(1690, 850, 0.75f, mountainSnowPath2, mountain, mountainSnow);
        drawMountain(2157, 920, 0.6f, mountainSnowPath1, mountain, mountainSnow);
    }

    private void drawMountain(int x, int y, float scale, Path snowStyle, int mountainColor, int mountainSnowColor) {
        canvas.save();
        canvas.translate(x, y);
        canvas.scale(scale, scale);

        paint.setColor(mountainColor);
        canvas.drawPath(mountainPath, paint);
        
        paint.setColor(mountainSnowColor);
        canvas.drawPath(snowStyle, paint);

        canvas.restore();
    }

    private void drawHouse() {
        int houseBase = ColorUtility.multiplyColors(houseBaseColor, lightColor);
        int houseDetails = ColorUtility.multiplyColors(houseDetailsColor, lightColor);
        paint.setStyle(Paint.Style.FILL);

        paint.setColor(houseBase);
        canvas.drawPath(houseBasePath, paint);

        paint.setColor(houseDetails);
        canvas.drawPath(houseRoofPath, paint);

        canvas.drawRect(1884, 777, 1900, 813, paint);
        canvas.drawRect(1916, 777, 1932, 813, paint);
        canvas.drawRect(1902, 836, 1919, 873, paint);
        canvas.drawRect(1934, 836, 1952, 873, paint);
        canvas.drawCircle(1829, 770, 10, paint);
        canvas.drawRect(1815, 847, 1841, 895, paint);
    }
}
