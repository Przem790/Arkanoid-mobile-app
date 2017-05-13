package com.example.przem.arkaido;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by przem on 08.05.2017.
 */

public class Ball {

    private float startX,tempX;
    private float startY,tempY;
    private float ballspeed,ballspeedX,ballspeedY;
    private float screenX;
    private float screenY;
    private float radius=30;

    public final int STOP=0,LEFT=1,RIGHT=2,TOP=1,BOT=2;
    private int BallmoveX=STOP;
    private int BallmoveY=STOP;
    private boolean ismoving;

    public Ball(int screenX,int screenY){
        this.screenY=screenY;
        this.screenX=screenX;
        startX=(screenX/2); //dokladnie srodek czyli ok
        startY=screenY-50;   //promien=30
        tempX=startX;
        tempY=startY;
        ballspeed=8;
        ballspeedX=8;
        ballspeedY=8;
        ismoving=false;

    }

    public float getX(){return tempX;}
    public float getY(){return tempY;}
    public boolean ifmove(){return ismoving;}
    public void startball(){
        ismoving=true;
        BallmoveX=RIGHT;
        BallmoveY=TOP;
        ballspeedX=ballspeed;
        ballspeedY=ballspeed;
    }

    public boolean update(float xleft, float xright){
        if(BallmoveX==LEFT){                                                                        //jeśli leci w lewo
            tempX=tempX-ballspeedX;
            if(!(tempX-radius>0)){
                BallmoveX=RIGHT;
            }
        }else if(BallmoveX==RIGHT){                                                                 //jeśli leci w prawo
            tempX=tempX+ballspeedX;
            if(!(tempX+radius<screenX)){
                BallmoveX=LEFT;
            }
        }

        if(BallmoveY==TOP){                                                                         //jeśli leci w góre
            tempY=tempY-ballspeedY;
            if(!(tempY-radius>0)){
                BallmoveY=BOT;
            }
        }else if(BallmoveY==BOT){                                                                   //jesli leci w dół
            tempY=tempY+ballspeedY;
            if(!(tempY+radius<screenY)){
                //BallmoveY=TOP;
                 ismoving=false;
                 BallmoveY=STOP;
                 BallmoveX=STOP;
                 tempX=startX;
                 tempY=startY;
                 return false;
            }

            if((tempY+radius>screenY-20)&&tempX<=xright&&tempX>=xleft){
                ballspeedX=((xright-xleft)/2);
                ballspeedX=ballspeedX-(tempX-xleft);
                boolean swapped=false;
                if(ballspeedX<0) {
                    swapped=true;
                    ballspeedX = -ballspeedX;
                }

                if(ballspeedX>70){
                    changedirectX(swapped);
                    ballspeedX=15;
                    ballspeedY=4;
                }else if(ballspeedX>60){
                    changedirectX(swapped);
                    ballspeedX=13;
                    ballspeedY=5;
                }else if(ballspeedX>50){
                    changedirectX(swapped);
                    ballspeedX=11;
                    ballspeedY=6;
                }else if(ballspeedX>40){
                    ballspeedX=9;
                    ballspeedY=7;
                }else if(ballspeedX>30){
                    ballspeedX=7;
                    ballspeedY=9;
                }else if(ballspeedX>20){
                    ballspeedX=6;
                    ballspeedY=11;
                }else if(ballspeedX>10){
                    ballspeedX=5;
                    ballspeedY=13;
                }else if(ballspeedX>0){
                    ballspeedX=4;
                    ballspeedY=15;
                }else {
                    ballspeedX = ballspeed;
                    ballspeedY = ballspeed;
                }
                BallmoveY=TOP;
            }

        }
        return true;
    }

    public boolean checkifhit(Brick brick){

        if(BallmoveX==LEFT){
            if(tempY-5<=brick.getRect().bottom&&tempY+5>=brick.getRect().top) {
                if (tempX - radius <= brick.getRect().right&& tempX >= brick.getRect().right) {
                    changestat();
                    BallmoveX = RIGHT;
                    return true;
                }
            }
        }else if(BallmoveX==RIGHT){                                                                 //jeśli leci w prawo
            if(tempY-5<=brick.getRect().bottom&&tempY+5>=brick.getRect().top) {
                if (tempX + radius >= brick.getRect().left && tempX <= brick.getRect().left) {
                    changestat();
                    BallmoveX = LEFT;
                    return true;
                }
            }
        }

        if(BallmoveY==TOP){                                                                         //jeśli leci w góre
            if(tempX+5>=brick.getRect().left&&tempX-5<=brick.getRect().right){
                if((tempY-radius<=brick.getRect().bottom)&&tempY>=brick.getRect().bottom){
                    changestat();
                    BallmoveY=BOT;
                    return true;
                }
            }
        }else if(BallmoveY==BOT) {                                                                   //jesli leci w dół
            if(tempX+5>=brick.getRect().left&&tempX-5<=brick.getRect().right) {
                if ((tempY + radius >= brick.getRect().top)&& tempY<= brick.getRect().top){
                    changestat();
                    BallmoveY = TOP;
                    return true;
                }
            }
        }
        return false;
    }
    public void changedirectX(boolean swapped){
        if(BallmoveX==LEFT&&swapped)
            BallmoveX=RIGHT;
        else if(BallmoveX==RIGHT&&!swapped)
            BallmoveX=LEFT;

    }


    public void changestat(){
        Random rnd =new Random();
        if(rnd.nextInt(100)<25){
            if(ballspeedY<8)
                ballspeedY++;
            else {
                if (rnd.nextInt(100) < 25) {
                    ballspeedY++;
                } else
                    ballspeedY--;
            }
        }
        if(rnd.nextInt(100)<25) {
            if (ballspeedX < 8)
                ballspeedX++;
            else {
                if (rnd.nextInt(100) < 25) {
                    ballspeedX++;
                } else
                    ballspeedX--;
            }
        }
    }

}
