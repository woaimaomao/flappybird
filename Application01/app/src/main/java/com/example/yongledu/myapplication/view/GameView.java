package com.example.yongledu.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.yongle.du.tools.ImageInfo;
import com.example.yongle.du.tools.ParseTexture;
import com.example.yongle.du.tools.SoundPlayTool;
import com.example.yongledu.myapplication.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by yongle.du on 2016/4/18.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    private static final String TAG = "GameView";
    private Canvas mCanvas;
    private Canvas cacheCanvas;
    private Paint mPaint ;
    private Bitmap cacheBitmap;
    private Bitmap srcBmp;
    private SurfaceHolder mHolder;
    private int mScreenWidth;
    private int mScreenHeight;
    private List<ImageInfo> imageInfos;
    private boolean state = false;
    private Map<String,ImageInfo> infoMap;
    private Thread mThread;
    private BackGround backGround = null;
    private Bitmap pipeBmp;
    private Bird bird;
    private Land land;
    private boolean isPause = false;
    private boolean isCrash = false;
    private List<Bitmap> birdBmps;
    private ArrayList<WaterPipe> scorePipes;
    private List<WaterPipe> pipes;
    private int mScore = 0;
    private ArrayList<Bitmap> numBmps;
    private Score score;
    private Guide guide;
    private Bitmap[] guides;
    private Bitmap[] crashes;
    private boolean isStart = false;
    private Crash crash;
    private boolean showCrash = false;
    private boolean restart = false;
    private int speedUp;

    public GameView(Context context) {
        super(context);
        initParas(context);
    }

    // 初始化第一步
    private void initParas(Context context) {
        infoMap = new HashMap<>();
        mHolder = getHolder();
        srcBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.fappybird);
        mHolder.addCallback(this);
        try {
            InputStream is = context.getAssets().open("flappbrid_texture.xml");
            imageInfos = new ParseTexture().parse(is);

            ImageInfo ii = null;
            for(int i=0 ;i<imageInfos.size(); i++){
                ii = imageInfos.get(i);
                infoMap.put(ii.getName(),ii);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        birdBmps = new ArrayList<>();
        pipes = new ArrayList<>();
        scorePipes = new ArrayList<>();
        numBmps = new ArrayList<>();
        guides = new Bitmap[2];
        crashes = new Bitmap[2];
        mPaint = new Paint();
        isPause = false;
        isStart = false;
        isCrash = false;
        showCrash = false;
        mScore = 0;
        speedUp = 5;
        // 提前准备声音
        SoundPlayTool.getInstance(getContext());
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mScreenWidth = width;
        mScreenHeight = height;
        initViews(width, height);
//        mThread.start();
    }

    // 初始化第二步
    private void initViews(int width, int height) {
        cacheBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas(cacheBitmap);
        // 初始化背景
        ImageInfo info = infoMap.get("background2.png");
        Bitmap backbmp = Bitmap.createBitmap(srcBmp,info.getX(),info.getY(),info.getWidth(),info.getHeight());
        backGround = new BackGround(backbmp,0,0,mScreenWidth,mScreenHeight);
        // 初始化水管
        ImageInfo pipeInfo = infoMap.get("waterpipe.png");
        pipeBmp = Bitmap.createBitmap(srcBmp, pipeInfo.getX(), pipeInfo.getY(), pipeInfo.getWidth(), pipeInfo.getHeight());
        WaterPipe pipe = new WaterPipe(pipeBmp,mScreenWidth, 0, mScreenWidth, mScreenHeight, 0,false);
        WaterPipe pipe1 = new WaterPipe(pipeBmp,mScreenWidth, 0, mScreenWidth, mScreenHeight, 0,true);
        pipes.add(pipe);
        scorePipes.add(pipe);
        pipes.add(pipe1);
        scorePipes.add(pipe1);

        // 创建一组小鸟(鸟翅膀要扇动)
        for (int i=0; i<3; i++) {
            ImageInfo birdInfo0 = infoMap.get("bird_blue00"+i+".png");
            Bitmap birdBmp0 = Bitmap.createBitmap(srcBmp, birdInfo0.getX(), birdInfo0.getY(), birdInfo0.getWidth(), birdInfo0.getHeight());
            birdBmps.add(birdBmp0);
        }
        bird = new Bird(birdBmps,200, 500, mScreenWidth, mScreenHeight);

        // 创建一个地面
        ImageInfo landInfo = infoMap.get("land.png");
        Bitmap landBmp = Bitmap.createBitmap(srcBmp, landInfo.getX(), landInfo.getY(), landInfo.getWidth(), landInfo.getHeight());
        land = new Land(landBmp,0, 0, mScreenWidth, mScreenHeight);

        // 创建一组数字(0-9)
        for(int i=0; i<10; i++) {
            ImageInfo numInfo = infoMap.get("number00"+i+".png");
            Bitmap numBmp = Bitmap.createBitmap(srcBmp, numInfo.getX(), numInfo.getY(), numInfo.getWidth(), numInfo.getHeight());
            numBmps.add(numBmp);
        }
        score = new Score(numBmps,mScreenWidth/3, mScreenHeight/5, mScreenWidth, mScreenHeight);

        // 创建引导界面
        ImageInfo readyInfo = infoMap.get("ready.png");
        Bitmap readyBmp = Bitmap.createBitmap(srcBmp, readyInfo.getX(), readyInfo.getY(), readyInfo.getWidth(), readyInfo.getHeight());
        guides[0] = readyBmp;
        ImageInfo guideInfo = infoMap.get("guide.png");
        Bitmap guideBmp = Bitmap.createBitmap(srcBmp, guideInfo.getX(), guideInfo.getY(), guideInfo.getWidth(), guideInfo.getHeight());
        guides[1] = guideBmp;
        guide = new Guide(guides,mScreenWidth/6,mScreenHeight/4+200,mScreenWidth,mScreenHeight);

        // 创建失败界面
        ImageInfo crashInfo0 = infoMap.get("gameOver.png");
        Bitmap crashBmp0 = Bitmap.createBitmap(srcBmp, crashInfo0.getX(), crashInfo0.getY(), crashInfo0.getWidth(), crashInfo0.getHeight());
        crashes[0] = crashBmp0;

        ImageInfo crashInfo1 = infoMap.get("play.png");
        Bitmap crashBmp1 = Bitmap.createBitmap(srcBmp, crashInfo1.getX(), crashInfo1.getY(), crashInfo1.getWidth(), crashInfo1.getHeight());
        crashes[1] = crashBmp1;
        crash = new Crash(crashes,mScreenWidth/6,mScreenHeight/4+100,mScreenWidth,mScreenHeight);

        // 线程标志开始
        state = true;
        draw();
        mThread = new Thread(this);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        state = false;
        isStart = false;
        showCrash = false;
    }


    /**
     * 主绘制函数
     */
    private synchronized void draw(){

        try {

            // 往缓存图片上绘制

            // 绘制背景
            backGround.drawSelf(cacheCanvas);
            //cacheCanvas.drawColor(Color.WHITE);
            // 绘制Pillar柱子
            for (int i = 0; i < pipes.size(); i++) {
                pipes.get(i).drawSelf(cacheCanvas);
            }


            // 绘制地面
            land.drawSelf(cacheCanvas);
            // 绘制小鸟
            bird.drawSelf(cacheCanvas);
            // 绘制分数
            score.drawSelf(cacheCanvas,mScore);

            // 游戏尚未开始
            if(!isStart){
                guide.drawSelf(cacheCanvas);
            }

            // 游戏失败
            if(showCrash){
                Log.i(TAG, "draw: showCrash:"+showCrash);
                crash.drawSelf(cacheCanvas);
                state = false;
            }

            // 正式绘制到Surface上
            mCanvas = mHolder.lockCanvas();
            // 清屏
            //mCanvas.drawColor(Color.WHITE);
            mCanvas.drawBitmap(cacheBitmap, 0, 0, null);
            mHolder.unlockCanvasAndPost(mCanvas);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // 处理游戏中精灵的逻辑
    private synchronized void logic(){

        // 小鸟下落的加速度
        speedUp+=2;
        // 如果碰撞则小鸟继续下移，其它不动
        if(isCrash){

            bird.flyToDie(bird.getY() + speedUp);
            if((bird.getY() + bird.getHeight()) > land.getY()+30){
                SoundPlayTool.getInstance(getContext()).playSound(0);
                showCrash = true;
                return;
            }
            return;
        }

        // 处理小鸟正常下落的逻辑
        bird.translateY(bird.getY() + speedUp);
        // 检测碰撞
        isCrash = checkCrash();


        // 判断是否得分
        achieveScore();
        // 处理水管的逻辑
        for (int i = 0; i< pipes.size(); i++){
            WaterPipe pillar = pipes.get(i);

            // 生成相对的两个水柱
            if(i == pipes.size()-1 && pillar.getX()+pillar.getWidth()  < 700){
                // 管道随机高度
                int grow = (int)(250*Math.random());
                // 每次生成两个对应的管道
                WaterPipe pipe0 = new WaterPipe(pipeBmp,mScreenWidth, 0, mScreenWidth, mScreenHeight,grow,true);
                WaterPipe pipe1 = new WaterPipe(pipeBmp,mScreenWidth, 0, mScreenWidth, mScreenHeight,grow,false);
                pipes.add(pipe0);
                scorePipes.add(pipe0);
                pipes.add(pipe1);
                scorePipes.add(pipe1);
                break;
            }


            // 移除超过屏幕的水柱
            if(pillar.getX()+pillar.getWidth() < 0){
                pipes.remove(pillar);
                break;
            }else { // 改变水柱的坐标，使其不断向左移动
                int x = pillar.getX();
//                Log.i("DYL","x = "+x);
                x-=10;
                pillar.setX(x);
            }
        }
    }

    // 判断是否可以获取分数
    private synchronized void achieveScore() {

        int size = scorePipes.size();

        for(int i=0; i<size; i++){
            WaterPipe pipe  = scorePipes.get(i);
            if(pipe.isUpPipe() && pipe.getX() + pipe.getWidth() < bird.getX()+bird.getWidth()){
                scorePipes.remove(i);
                SoundPlayTool.getInstance(getContext()).playSound(2);
                mScore++;
                return;
            }
        }
    }

    // 碰撞检测函数
    private boolean checkCrash() {

        // 不能碰天花板
        if(bird.getY() <= 0){
            // 播放死亡音效
            SoundPlayTool.getInstance(getContext()).playSound(1);
            return true;
        }

        // 不能碰地板
        if(bird.getY() + bird.getHeight() >= land.getY()){
            // 播放死亡音效
            SoundPlayTool.getInstance(getContext()).playSound(1);
            return true;
        }

        // 不能碰到水管
        for(int i=0; i < scorePipes.size(); i++) {
            WaterPipe pipe = scorePipes.get(i);
            if (pipe.isUpPipe()) {
                if ((bird.getX() + bird.getWidth() > pipe.getX())
                        && (bird.getY() < pipe.getY() + pipe.getHeight())
                        && (bird.getX() < pipe.getX()+pipe.getWidth())) {
                    // 播放死亡音效
                    SoundPlayTool.getInstance(getContext()).playSound(1);
                    return true;
                }
            } else {
                if ((bird.getX() + bird.getWidth() > pipe.getX())
                        && (bird.getHeight() + bird.getY()) > pipe.getY()
                        && (bird.getX() < pipe.getX()+pipe.getWidth())) {
                    SoundPlayTool.getInstance(getContext()).playSound(1);
                    return true;

                }
            }
        }
        return false;
    }

    // 循环绘制界面
    @Override
    public void run() {
        while (state){

            while(isPause){
                try {
                    synchronized (mThread) {
                        mThread.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

                long start = System.currentTimeMillis();

                draw();
                logic();
                long end = System.currentTimeMillis();
            try {
                long time = 30<(end -start)?(end-start)-30:(end-start);
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int tx = (int) event.getX();
                int ty = (int) event.getY();
                if(showCrash){
                    if(tx > crash.getX()
                       && tx < crash.getX()+crash.getWidth()
                       && ty > crash.getY()
                       && ty < crash.getY() + crash.getHeight()){
                        SoundPlayTool.getInstance(getContext()).playSound(3);
                        reset();
                        restart = true;
                    }
                    return true;
                }
                if(!isCrash){
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(isStart) {
                    if (!isCrash) {
                        // 播放点击屏幕音效
                        SoundPlayTool.getInstance(getContext()).playSound(4);
                        speedUp = 5;
                        bird.flyUp(bird.getY() - 150);
                        return true;
                    }
                }else if(!restart){
                    isStart = true;
                    mThread.start();
                }else if (restart){
                    restart = false;
                    showCrash = false;
                }
                break;
        }


        return false;
    }

    // 停止游戏
    public void stopGame() {
        if(mThread.isAlive()){
            state = false;
            isStart = false;
            mThread.interrupt();
        }
    }

    private void reset(){
        initParas(getContext());
        initViews(mScreenWidth,mScreenHeight);
    }

    // 暂停游戏
    public void pauseGame() {

            isPause = true;
    }

    public void continueGame() {
        if(isPause){
            isPause = false;
            mThread.interrupt();
        }
    }
}
