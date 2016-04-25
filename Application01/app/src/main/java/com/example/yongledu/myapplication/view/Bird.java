package com.example.yongledu.myapplication.view;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.List;
import java.util.Random;

/**
 * Created by yongle.du on 2016/4/20.
 */
public class Bird implements GameImage {

    private int x;
    private int y;
    private int width;
    private int height;
    private int screenX;
    private int screenY;
    private Paint mPaint;
    private Bitmap bmp;
    private RectF dst;
    private List<Bitmap> birds;
    private Matrix matrix;
    private int degree = 0;

    public Bird(List<Bitmap> bitmaps, int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.screenX = width;
        this.screenY = height;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        matrix = new Matrix();

//        dst = new Rect(x,y,screenX/7+x,screenY/9+y);
        birds = bitmaps;
        bmp = bitmaps.get(0);
        dst = new RectF(x,y,bmp.getWidth()*4+x,bmp.getHeight()*4+y);

        this.width = (int)(dst.right - dst.left);
        this.height = (int)(dst.bottom - dst.top);

    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
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

    public void traslateX(int x) {
        this.x = x;
        int tmpX = (int)(dst.left - x);
        dst.set(x,dst.top,dst.right - tmpX,dst.bottom);
        Log.i("DYL","width = "+width);
    }

    /**
     * 正常下落
     * @param y
     */
    public void translateY(int y) {

            int tmpY = (int)(dst.top - y);
            this.y = y;
//            degree = MULTIPLE;
            dst.set(x, this.y, dst.right, dst.bottom - tmpY);
    }

    /**
     * 死亡移动
     * @param y
     */
    public void flyToDie(int y) {
        int tmpY = (int)(dst.top - y);
        this.y = y;
        degree -=20;
        dst.set(x,this.y,dst.right,dst.bottom - tmpY);
    }

    /**
     * 向上飞
     * @param y
     */
    private final int MULTIPLE = 30;
    public void flyUp(int y) {
            int tmpY = (int) (dst.top - y);
            this.y = y;
//            degree = -MULTIPLE;
            dst.set(x, this.y, dst.right, dst.bottom - tmpY);
    }


    private int index = 0;
    @Override
    public void drawSelf(Canvas canvas) {
        if(index == 3){
            index = 0;
        }
        canvas.save();
        canvas.rotate(degree,this.getX()+this.getWidth()/2,this.getY() + this.getHeight()/2);
        canvas.drawBitmap(birds.get(index ++),null,dst,mPaint);
        canvas.restore();
    }
}
