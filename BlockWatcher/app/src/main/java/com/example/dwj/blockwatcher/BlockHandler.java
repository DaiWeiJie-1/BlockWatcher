package com.example.dwj.blockwatcher;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.example.dwj.blockwatcher.notification.BlockNotificationManager;
import com.example.dwj.demo.MainActivity;

/**
 * Created by dwj on 2016/5/29.
 */
public class BlockHandler implements IPrinter{

    private final static String DISPATCH_LOG = ">>>>> Dispatching to ";
    private final static String FINISH_LOG = "<<<<< Finished to ";

    private int mThresholdTime = 0;
    private long mDispatchHanderTime = 0;
    private long mFinshHandlerTime = 0;
    private Thread mThread;
    private Collector mCollector = null;
    private LooperHandlerState mState = LooperHandlerState.FINISH;
    private Context mContext;

    public BlockHandler(Context context,int thresholdTime,Thread thread){
        this.mThresholdTime = thresholdTime;
        this.mThread = thread;
        this.mContext = context;
        this.mCollector = new Collector((int)(mThresholdTime * 0.5),0,mThread);
    }

    @Override
    public void println(String x) {
        handlerMsg(x);
    }

    private void handlerMsg(String x){
        if(TextUtils.isEmpty(x)){
            return;
        }

        if(x.contains(DISPATCH_LOG)){
            mCollector.startCollect();
            mDispatchHanderTime = System.currentTimeMillis();
            mState = LooperHandlerState.DISPATCH;
        }else if(x.contains(FINISH_LOG)){
            if(System.currentTimeMillis() - mDispatchHanderTime >= 5000){
                mCollector.stopCollect();
                mCollector.showCacheSomething();
                BlockNotificationManager.getInstance().showBlockInfoNotification(mContext,mCollector.getBlockInfo());

                Intent it = new Intent(mContext,MainActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                it.addCategory(Intent.CATEGORY_LAUNCHER);
                it.setAction(Intent.ACTION_MAIN);
                mContext.startActivity(it);
                Process.killProcess(Process.myPid());
            }


            if(mState == LooperHandlerState.DISPATCH){
                mFinshHandlerTime = System.currentTimeMillis();
                if(mFinshHandlerTime - mDispatchHanderTime >= mThresholdTime){
                    mCollector.stopCollect();
                    mCollector.showCacheSomething();
                    BlockNotificationManager.getInstance().showBlockInfoNotification(mContext,mCollector.getBlockInfo());
                }

            }

            mState = LooperHandlerState.FINISH;
            mDispatchHanderTime = 0;
            mFinshHandlerTime = 0;
        }else{

        }

    }


    enum LooperHandlerState{

        DISPATCH(0),

        FINISH(1);

        private int state;

        private LooperHandlerState(int state){
            this.state = state;
        }

        private int getState(){
            return state;
        }

        public static LooperHandlerState getLooperHandlerState(int state){
            if(DISPATCH.state == state){
                return DISPATCH;
            }else if(FINISH.state == state){
                return FINISH;
            }else {
                throw new IllegalArgumentException("Not support state");
            }


        }
    }
}
