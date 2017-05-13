package com.example.przem.arkaido;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class Arkaido extends Activity {
    ArkaidoView arkaidoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arkaidoview = new ArkaidoView(this);
        setContentView(arkaidoview);
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        arkaidoview.setListeners(sm, arkaidoview.mEventListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        arkaidoview.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        arkaidoview.pause();
    }

    class ArkaidoView extends SurfaceView implements Runnable, Serializable {
        Thread gameThread = null;
        SurfaceHolder ourHolder;
        volatile boolean playing;
        boolean paused = true;
        Canvas canvas;
        Paint paint;
        int screenX;
        int screenY;
        Pad pad;
        Ball ball;
        StageDesigner stagedesigner;
        ArrayList<Brick> bricks;
        public boolean isgravityenabled;
        ArkaidoView thisview;

        final SensorEventListener mEventListener = new SensorEventListener() {                       //OBSLUGA SENSORA GRAVITY
            @Override
            public void onSensorChanged(SensorEvent event) {
                float xChange = event.values[0];
                if (isgravityenabled) {
                    if (xChange < 0) {
                        pad.setMovementState(pad.RIGHT);
                    } else if (xChange > 0) {
                        pad.setMovementState(pad.LEFT);
                    } else if (xChange == 0)
                        pad.setMovementState(pad.STOP);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //empty body
            }
        };


        public ArkaidoView(Context context) {
            super(context);
            ourHolder = getHolder();
            paint = new Paint();
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenX = size.x;
            screenY = size.y;
            pad = new Pad(screenX, screenY);
            ball = new Ball(screenX, screenY);
            stagedesigner = new StageDesigner(screenX,screenY);
            bricks=stagedesigner.getGeneratedLevel();
            isgravityenabled=true;
            thisview=this;
        }

        @Override
        public synchronized void run() {
            while (playing) {
                if (!paused) {
                    pad.update();
                    for(Brick x:bricks) {
                        if (x.getLevel() >= 0 && x.getLevel()!=4) {
                            if (ball.checkifhit(x)) {
                                if(x.getLevel()!=3) {
                                    x.lvldown();
                                }
                            }
                        }
                    }
                    if (!ball.update(pad.getRect().left, pad.getRect().right)) {
                        pad.setToStart();
                        paused = true;
                    }

                }
                draw();
            }

        }

        public void update() {

        }

        public synchronized void draw() {
            if (ourHolder.getSurface().isValid()) {
                canvas = ourHolder.lockCanvas();

                canvas.drawColor(Color.argb(255, 26, 63, 185));
                paint.setColor(Color.argb(255, 255, 255, 255));
                canvas.drawRect(pad.getRect(), paint);
                canvas.drawCircle(ball.getX(), ball.getY(), 30, paint);
                for(Brick xx:bricks){
                    if(xx.getLevel()==3){
                        paint.setColor(Color.argb(255, 0, 0, 0));
                    }
                    else if(xx.getLevel()==2){
                        paint.setColor(Color.argb(255, 60, 71, 66));
                    }
                    else if(xx.getLevel()==1){
                        paint.setColor(Color.argb(255, 100, 117, 109));
                    }
                    else if(xx.getLevel()==0){
                        paint.setColor(Color.argb(255, 112, 168, 142));
                    }else continue;
                    canvas.drawRect(xx.getRect(),paint);
                }
                paint.setColor(Color.argb(255, 255, 255, 255));
                canvas.drawRect(new RectF(screenX-40,10,screenX-30,50),paint);          //STOP GAME
                canvas.drawRect(new RectF(screenX-20,10,screenX-10,50),paint);          //STOP GAME
                ourHolder.unlockCanvasAndPost(canvas);

            }

        }

        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }

        }

        // The SurfaceView class implements onTouchListener
        // So we can override this method and detect screen touches.

        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        public void setListeners(SensorManager sensorManager, SensorEventListener mEventListener)               //SENSOR GRAVITY DODAJE
        {
            sensorManager.registerListener(mEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                    SensorManager.SENSOR_DELAY_GAME);
            sensorManager.registerListener(mEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                    SensorManager.SENSOR_DELAY_GAME);
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN:
                    if (!ball.ifmove()) {
                        ball.startball();
                    }
                    paused = false;
                    if(motionEvent.getX()>screenX-50&&motionEvent.getY()<50){
                        paused = true;
                        Arkaido.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("object", thisview);
                                SwitchDialog DD = new SwitchDialog();
                                DD.setArguments(bundle);
                                DD.show(getFragmentManager()," ");
                            }
                        });
                    }
                    else if(motionEvent.getX() > screenX / 2){
                        if(!isgravityenabled)
                        pad.setMovementState(pad.RIGHT);
                    }else{
                        if(!isgravityenabled)
                        pad.setMovementState(pad.LEFT);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if(!isgravityenabled)
                    pad.setMovementState(pad.STOP);
                    break;
            }
            return true;
        }
    }


}




