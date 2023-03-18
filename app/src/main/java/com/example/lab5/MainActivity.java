package com.example.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.lab5.engine.Engine;

public class MainActivity extends AppCompatActivity {

    SurfaceView surface;
    Engine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surface = findViewById(R.id.surface);
        //в момент создания запускается поток
        engine = new Engine(surface);
        //Callback служит для управления поверхностью
        surface.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            //когда поверхность создана и готова для рисования
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                //пока находися в этом режиме другой поток не может рисовать поверхность захваченную в эксклюз режиме
                Canvas canvas = surfaceHolder.lockCanvas();
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                //задаем цвет черчения
                paint.setColor(Color.WHITE);
                // стиль черчения
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(3);
                canvas.drawPoint(100,100, paint);
                //разблокировка холста и показ отрисованного изображения
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {//поверхность уничтожена

            }
        });

    }

    @Override
    protected void onDestroy() {
        engine.stop();
        super.onDestroy();
    }
}
