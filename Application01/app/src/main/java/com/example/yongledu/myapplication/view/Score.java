package com.example.yongledu.myapplication.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yongle.du on 2016/4/22.
 */
public class Score implements GameImage{


    private int x;
    private int y;
    private int width;
    private int height;
    private int screenX;
    private int screenY;
    private Bitmap bmp;
    private Paint mPaint;
    private List<Bitmap> bitmaps;
    private Rect dst1;
    private Rect dst2;
    private Rect dst3;
    private int mScore = 0;
    private final int SCALE = 3;

    public Score(ArrayList<Bitmap> bmps, int x, int y, int width, int height){
        this.bitmaps = bmps;
        this.bmp = bitmaps.get(0);
        this.dst1 = new Rect(x,y,x+bmp.getWidth()*SCALE,y+bmp.getHeight()*SCALE);
        this.dst2 = new Rect(dst1.right, y, dst1.right+bmp.getWidth()*SCALE, dst1.bottom);
        this.dst3 = new Rect(dst2.right,y, dst2.right + bmp.getWidth()*SCALE, dst2.bottom);
        this.x = x;
        this.y = y;
        this.screenX = width;
        this.screenY = height;
        this.width = this.bmp.getWidth();
        this.height = this.bmp.getHeight();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
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

        if(mScore < 0)mScore = 0;
        int ge = mScore%10;
        int shi = mScore/10%10;
        int bai = mScore/100;
        canvas.drawBitmap(bitmaps.get(ge),null,dst3,mPaint);
        if(shi != 0) {
            canvas.drawBitmap(bitmaps.get(shi),null,dst2,mPaint);
        }
        if(bai != 0){
            canvas.drawBitmap(bitmaps.get(bai),null,dst1,mPaint);
        }
    }

    public void drawSelf(Canvas canvas, int score) {
//        Rect src = new Rect(0,0,bmp.getWidth(),bmp.getHeight());
//        Log.i("DYL","src=="+src.right+","+src.bottom);

//        Log.i("DYL","dst=="+dst.right+","+dst.bottom);

          this.mScore = score;
          drawSelf(canvas);
    }
}
