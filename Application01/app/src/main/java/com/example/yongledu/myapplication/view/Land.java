package com.example.yongledu.myapplication.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

/**
 * Created by yongle.du on 2016/4/20.
 */
public class Land implements GameImage {

    private int x;
    private int y;
    private int width;
    private int height;
    private int screenX;
    private int screenY;
    private Paint mPaint;
    private Bitmap bmp;
    private Rect dst;

    public Land(Bitmap bitmap, int x, int y, int width, int height){

        this.screenX = width;
        this.screenY = height;
        mPaint = new Paint();
        this.bmp = bitmap;

        dst = new Rect(x,screenY*5/6,screenX,screenY);

        this.width = dst.right - dst.left;
        this.height = dst.bottom - dst.top;


    }

    @Override
    public int getX() {
        this.x = dst.left;

        return this.x;
    }

    @Override
    public int getY() {
        this.y = dst.top;
        return this.y;
    }



    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
        int tmpX = dst.left - x;
        dst.set(x,dst.top,dst.right - tmpX,dst.bottom);
//        Log.i("DYL","width = "+width);
    }

    public void setY(int y) {
        this.y = y;
        int tmpY = dst.top - y;
        dst.set(x,this.y,dst.right,dst.bottom - tmpY);
    }


    Matrix matrix = new Matrix();
    @Override
    public void drawSelf(Canvas canvas) {

        canvas.drawBitmap(bmp,null,dst,mPaint);
    }
}
