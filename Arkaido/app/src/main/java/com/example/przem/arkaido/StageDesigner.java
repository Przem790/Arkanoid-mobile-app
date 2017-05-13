package com.example.przem.arkaido;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by przem on 08.05.2017.
 */

public class StageDesigner {
    ArrayList<Brick> map;
    float screenX,screenY;
    public StageDesigner(float X,float Y){
        this.screenX=X;
        this.screenY=Y;

    }


    public ArrayList<Brick> getGeneratedLevel(){
        map = new ArrayList<>();
        float lenght =(screenX/8)-1;
        float height =((45*screenY)/1000)-1;
        float tempx=0,tempy=0;

        for(int k=0;k<10;k++) {
            for (int i = 0; i < 8; i++) {

                    map.add(new Brick(lenght,height,tempx,tempy));

                tempx+=lenght+1;
            }
            tempy+=height+1;
            tempx=0;
        }
        return map;
    }
}
