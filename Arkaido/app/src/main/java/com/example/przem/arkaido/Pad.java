package com.example.przem.arkaido;
import android.graphics.RectF;

/**
 * Created by przem on 07.05.2017.
 */

public class Pad {
    private RectF rect;
    private float lenght;
    private float height;
    private float x;
    private float y;
    private float paddleSpeed;
    private float screenX;

    public final int STOP=0,LEFT=1,RIGHT=2;
    private int padmovement=STOP;

    public Pad(int screenX,int screenY){
        lenght=160;
        height=20;
        this.screenX=screenX;
        x=(screenX/2)-80;
        y=screenY-20;

        rect= new RectF(x,y,x+lenght,y+height);
        paddleSpeed=10;

    }
    public void setMovementState(int state){
        padmovement=state;
    }
    public void setToStart(){
        x=(screenX/2)-80;
        rect.left=x;
        rect.right=x+lenght;
    }
    public RectF getRect(){
        return rect;
    }
    public void update(){
        if(padmovement==LEFT&&x>0){
            x=x-paddleSpeed;
        }

        if(padmovement==RIGHT&&x+lenght<screenX){
            x=x+paddleSpeed;
        }

        rect.left=x;
        rect.right=x+lenght;
    }

}
