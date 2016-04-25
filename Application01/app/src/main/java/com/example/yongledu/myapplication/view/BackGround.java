package com.example.yongledu.myapplication.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by yongle.du on 2016/4/18.
 */
public class BackGround implements GameImage {

    private int x;
    private int y;
    private int width;
    private int height;
    private int screenX;
    private int screenY;
    private Bitmap bmp;
    private Paint mPaint;

    public BackGround(Bitmap bmp,int x,int y,int width,int height){
        this.bmp = bmp;
        this.x = x;
        this.y = y;
        this.screenX = width;
        this.screenY = height;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void drawSelf(Canvas canvas) {
        Rect src = new Rect(0,0,bmp.getWidth(),bmp.getHeight());
//        Log.i("DYL","src=="+src.right+","+src.bottom);
        Rect dst = new Rect(x,y,screenX,screenY);
//        Log.i("DYL","dst=="+dst.right+","+dst.bottom);
        canvas.drawBitmap(bmp,src,dst,mPaint);
    }
}
