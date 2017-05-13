/**
 package com.example.przem.arkaido;

 import android.app.Activity;
 import android.app.ActivityManager;
 import android.content.Context;
 import android.graphics.Bitmap;
 import android.graphics.BitmapFactory;
 import android.graphics.Canvas;
 import android.graphics.Color;
 import android.graphics.Paint;
 import android.graphics.Point;
 import android.graphics.drawable.Drawable;
 import android.hardware.Sensor;
 import android.hardware.SensorEvent;
 import android.hardware.SensorEventListener;
 import android.hardware.SensorManager;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.Display;
 import android.view.MotionEvent;
 import android.view.SurfaceHolder;
 import android.view.SurfaceView;

 public class Arkaido extends Activity {
 ArkaidoView arkaidoview;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 arkaidoview = new ArkaidoView(this);
 setContentView(arkaidoview);
 SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
 arkaidoview.setListeners(sm,arkaidoview.mEventListener);
 }


 class ArkaidoView extends SurfaceView implements Runnable {
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

 public ArkaidoView(Context context) {
 super(context);
 ourHolder = getHolder();
 paint = new Paint();
 Display display = getWindowManager().getDefaultDisplay();
 Point size = new Point();
 display.getSize(size);
 screenX=size.x;
 screenY=size.y;
 pad=new Pad(screenX,screenY);
 ball=new Ball(screenX,screenY);

 }

 @Override
 public void run() {
 while (playing) {
 if(!paused){
 pad.update();

 if(!ball.update(pad.getRect().left,pad.getRect().right)){
 pad.setToStart();
 paused=true;

 };
 }
 draw();
 }

 }

 public void update() {

 }


 public void draw() {
 if (ourHolder.getSurface().isValid()) {
 canvas = ourHolder.lockCanvas();

 canvas.drawColor(Color.argb(255,  26, 128, 182));
 paint.setColor(Color.argb(255,  255, 255, 255));
 canvas.drawRect(pad.getRect(),paint);
 canvas.drawCircle(ball.getX(),ball.getY(),30,paint);
 // Draw the paddle

 // Draw the ball

 // Draw the bricks

 // Draw the HUD

 // Draw everything to the screen
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

 public void resume() {
 playing = true;
 gameThread = new Thread(this);
 gameThread.start();
 }

 // The SurfaceView class implements onTouchListener
 // So we can override this method and detect screen touches.



 final SensorEventListener mEventListener = new SensorEventListener() {                       //OBSLUGA SENSORA GRAVITY
 @Override
 public void onSensorChanged(SensorEvent event) {
 float xChange =event.values[0];

 if (xChange < 0){
 pad.setMovementState(pad.RIGHT);
 }
 else if (xChange > 0){
 pad.setMovementState(pad.LEFT);
 }else if(xChange==0)
 pad.setMovementState(pad.STOP);
 }

 @Override
 public void onAccuracyChanged(Sensor sensor, int accuracy) {
 //empty body
 }
 };



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
 if(!ball.ifmove()) {
 ball.startball();
 }
 paused = false;
 break;
 case MotionEvent.ACTION_UP:
 //pad.setMovementState(pad.STOP);
 break;
 }
 return true;
 }
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


 }



 */