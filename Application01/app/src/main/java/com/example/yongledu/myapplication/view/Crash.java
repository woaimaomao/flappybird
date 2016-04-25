package com.example.yongledu.myapplication.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Bill-Gates on 2016-4-24.
 */
public class Crash implements GameImage{
    private int x;
    private int y;
    private int width;
    private int height;
    private int screenX;
    private int screenY;
    private Bitmap bmp;
    private Paint mPaint;
    private Bitmap[] bitmaps;
    private Rect dstOver;
    private Rect dstPlay;
    private final int SCALE_READY_X = 4;
    private final int SCALE_READY_Y = 3;
    private final int SCALE_GUIDE = 3;

    public Crash(Bitmap[] bmps, int x, int y, int width, int height){
        this.bitmaps = bmps;
        this.bmp = bitmaps[0];
        this.dstOver = new Rect(x, y, x+bmp.getWidth()*SCALE_READY_X, y+bmp.getHeight()*SCALE_READY_Y);
         this.dstPlay= new Rect(x, dstOver.bottom, x+bitmaps[1].getWidth()*SCALE_GUIDE, dstOver.bottom + bitmaps[1].getHeight()*SCALE_GUIDE);
        this.x = dstPlay.left;
        this.y = dstPlay.top;
        this.screenX = width;
        this.screenY = height;
        this.width = dstPlay.right - dstPlay.left;
        this.height = dstPlay.bottom - dstPlay.top;
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

        canvas.drawBitmap(bitmaps[0],null, dstOver,mPaint);
        canvas.drawBitmap(bitmaps[1],null, dstPlay,mPaint);

    }

}
