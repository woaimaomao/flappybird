package com.example.yongledu.myapplication.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by yongle.du on 2016/4/20.
 */
public class WaterPipe implements GameImage {

    private int x;
    private int y;
    private int width;
    private int height;
    private int realWidth;
    private int realHeight;
    private Paint mPaint;
    private Bitmap bmp;
    private Rect dst;
    private boolean isUpPipe;

    public WaterPipe(Bitmap bitmap, int x, int y, int width, int height,int grow, boolean b){

        this.realWidth = width;
        this.realHeight = height;
        mPaint = new Paint();
        // 标记为上水柱或是下水柱
        isUpPipe = b;
        if(isUpPipe){
            matrix.postRotate(180);
            dst = new Rect(x,y, realWidth /4+x, realHeight *3/7 + grow);
        }else{
//            matrix.postTranslate(x,realHeight - (dst.bottom - dst.top));
            dst = new Rect(x, realHeight - (realHeight /3 - y)+grow, realWidth /4+x, realHeight);
        }
        this.x = dst.left;
        this.y = dst.top;

        this.width = dst.right - dst.left;
        this.height = dst.bottom - dst.top;
        this.bmp= Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);

    }

    public boolean isUpPipe(){
        return isUpPipe;
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

    public void setX(int x) {
        int daltX = dst.left - x;
        dst.set(x,dst.top,dst.right - daltX,dst.bottom);
        this.x = dst.left;
    }

    public void setY(int y) {
        this.y = y;
    }


    Matrix matrix = new Matrix();
    @Override
    public void drawSelf(Canvas canvas) {

        canvas.drawBitmap(bmp,null,dst,mPaint);
    }

    @Override
    public String toString() {
        return "[x="+x+",y="+y+",width="+width+",height="+height+"]";
    }
}
