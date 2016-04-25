package com.example.yongledu.myapplication.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bill-Gates on 2016-4-24.
 */
public class Guide implements GameImage {

    private final int offset = 100;
    private int x;
    private int y;
    private int width;
    private int height;
    private int screenX;
    private int screenY;
    private Bitmap bmp;
    private Paint mPaint;
    private Bitmap[] bitmaps;
    private Rect dstGuide;
    private Rect dstReady;
    private final int SCALE_READY_X = 4;
    private final int SCALE_READY_Y = 3;
    private final int SCALE_GUIDE = 3;

    public Guide(Bitmap[] bmps, int x, int y, int width, int height){
        this.bitmaps = bmps;
        this.bmp = bitmaps[0];
        this.dstReady = new Rect(x, y, x+bmp.getWidth()*SCALE_READY_X, y+bmp.getHeight()*SCALE_READY_Y);
        this.dstGuide = new Rect(dstReady.left+offset,
                                 dstReady.bottom,
                                 dstReady.left+offset+bmp.getWidth()*SCALE_GUIDE,
                                 dstReady.bottom + bitmaps[1].getHeight()*SCALE_GUIDE);
        this.x = x;
        this.y = y;
        this.screenX = width;
        this.screenY = height;
        // width and height is wrong
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

        canvas.drawBitmap(bitmaps[0],null,dstReady,mPaint);
        canvas.drawBitmap(bitmaps[1],null,dstGuide,mPaint);

    }

}
