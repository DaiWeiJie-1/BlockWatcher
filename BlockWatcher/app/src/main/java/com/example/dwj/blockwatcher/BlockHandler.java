package com.example.dwj.blockwatcher;

import android.text.TextUtils;
import android.util.Log;

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

    public BlockHandler(int thresholdTime,Thread thread){
        this.mThresholdTime = thresholdTime;
        this.mThread = thread;
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
            mDispatchHanderTime = System.currentTimeMillis();
            String stackTraceInfo = ThreadStackTraceUtil.getThreadStackInfoStr(mThread);
        }else if(x.contains(FINISH_LOG)){
            if(mDispatchHanderTime != 0){
                mFinshHandlerTime = System.currentTimeMillis();
                if(mFinshHandlerTime - mDispatchHanderTime >= mThresholdTime){
                    String stackTraceInfo = ThreadStackTraceUtil.getThreadStackInfoStr(mThread);
                    Log.d("block",stackTraceInfo);
                }

            }

            mDispatchHanderTime = 0;
            mFinshHandlerTime = 0;
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
