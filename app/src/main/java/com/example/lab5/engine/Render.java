package com.example.lab5.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Render {

    Paint paint;

    public Render() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(3);
    }

    public void draw(Canvas canvas, Model model) {
        paint.setColor(Color.BLACK);
        //рисовка заполненного треуголника
        paint.setStyle(Paint.Style.FILL);
        //очистка экрана
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
        //переключение режима рисования на черчение
        paint.setStyle(Paint.Style.STROKE);
        for (Point point: model.getPoints()) {
            float sx = canvas.getWidth() / 2f + (canvas.getWidth() / 2f * point.x / point.z);
            float sy = canvas.getHeight() / 2f + (canvas.getHeight() / 2f * point.y / point.z);

            int isx = Math.round(sx);
            int isy = Math.round(sy);

            if (isx < canvas.getWidth() && isx >= 0
                    && isy < canvas.getHeight() && isy >= 0) {
                float colorGain = ((float)(255 + (int)(point.z * (255 / Math.abs(Model.INITIAL_Z_COORD))))) / 255f;
                //значение каждой компоненты достается отдельно
                int colorR = (point.color & 0xff0000) >> 16;
                int colorG = (point.color & 0xff00) >> 8;
                int colorB = (point.color & 0xff);
                //  Правило: чем дальше точка, тем она темнее, чем ближе точка, тем она светлее.
                // вычисление яркости каждой компоненты с учетом расстояния и все складывается в один цвет
                paint.setColor(0xff000000
                        | (int)(colorR * colorGain) << 16
                        | (int)(colorG * colorGain) << 8
                        | (int)(colorB * colorGain));
                //рисование точки
                canvas.drawPoint(isx, isy, paint);
            }
        }
    }
}