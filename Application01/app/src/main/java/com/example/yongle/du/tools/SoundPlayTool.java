package com.example.yongle.du.tools;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.yongledu.myapplication.R;

/**
 * Created by yongle.du on 2016/4/22.
 */
public class SoundPlayTool {

    private SoundPool pool;
    private Context mContext;
    private static SoundPlayTool mSoundPlayTool ;
    private int[] sounds = new int[5];
    private enum SOUND{
        DIE,HIT,POINT,SWOOSHING,WING
    }
    private SoundPlayTool(Context context){
        this.mContext = context;
        pool = new SoundPool(20, AudioManager.STREAM_SYSTEM,0);
        sounds[0] = pool.load(context, R.raw.sfx_die,10);
        sounds[1] = pool.load(context, R.raw.sfx_hit,10);
        sounds[2] = pool.load(context, R.raw.sfx_point,10);
        sounds[3] = pool.load(context, R.raw.sfx_swooshing,10);
        sounds[4] = pool.load(context, R.raw.sfx_wing,10);
    }

    public static SoundPlayTool getInstance(Context context){

        if(mSoundPlayTool == null){
            mSoundPlayTool = new SoundPlayTool(context);
        }

        return mSoundPlayTool;
    }

    /**
     * 播放游戏音效
     * @param sound 0-DIE,1-HIT,2-POINT,3-SWOOSHING,4-WING
     */
    public void playSound(int sound){
        pool.play(sounds[sound],1,1,1,0,1);
        /*switch (sound){
            case SOUND.DIE:
                pool.play(sounds[0],1,1,1,0,1);
                break;
            case SOUND.HIT:
                pool.play(sounds[0],1,1,1,0,1);
                break;
            case SOUND.POINT:
                pool.play(sounds[0],1,1,1,0,1);
                break;
            case SOUND.SWOOSHING:
                pool.play(sounds[0],1,1,1,0,1);
                break;
            case SOUND.WING:
                pool.play(sounds[0],1,1,1,0,1);
                break;
        }*/
    }
}
