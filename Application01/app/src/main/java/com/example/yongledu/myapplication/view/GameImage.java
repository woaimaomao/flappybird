package com.example.yongledu.myapplication.view;

import android.graphics.Canvas;

/**
 * Created by yongle.du on 2016/4/18.
 */
public interface GameImage {

    public int getX();
    public int getY();
    public void drawSelf(Canvas canvas);
    public int getWidth();
    public int getHeight();
}
