package com.example.przem.arkaido;

import android.graphics.RectF;


import java.util.Random;



public class Brick {
    private int level;
    private RectF rect;
    private float lenght;
    private float height;
    private float x;
    private float y;

    public Brick(float lenght,float height,float x,float y){
        this.lenght=lenght;
        this.height=height;
        this.x=x;
        this.y=y;
        Random rnx= new Random();
        level=rnx.nextInt(3);
        if(rnx.nextInt(100)<=5){
            if(rnx.nextInt(100)<=50){
                level=3;
            }else
                level=4;
        }
        rect= new RectF(x,y,x+lenght,y+height);
    }

    public void lvldown(){
        level--;
    }
    public int getLevel(){return level;}
    public RectF getRect(){return rect;}

}
