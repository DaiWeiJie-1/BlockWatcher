package com.example.dwj.blockwatcher;

import android.content.Context;
import android.text.TextUtils;

import com.example.dwj.blockwatcher.outputter.IOutputter;
import com.example.dwj.blockwatcher.outputter.NotificationOutputter;

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
    private IOutputter mIOutputter = null;

    public BlockHandler(Context context,int thresholdTime,Thread thread){
        this.mThresholdTime = thresholdTime;
        this.mThread = thread;
        this.mContext = context;
        this.mCollector = new Collector(mContext,(int)(mThresholdTime * 0.5),0,mThread);
        this.mIOutputter = new NotificationOutputter(mContext);
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
            if(mState == LooperHandlerState.DISPATCH){
                mFinshHandlerTime = System.currentTimeMillis();
                if(mFinshHandlerTime - mDispatchHanderTime >= mThresholdTime){
                    mCollector.showCacheSomething();
                    mIOutputter.outPutBlockInfo(mCollector.getBlockInfo());
                }
                mCollector.stopCollect();
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
