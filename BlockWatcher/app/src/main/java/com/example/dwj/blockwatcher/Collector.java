package com.example.dwj.blockwatcher;

import android.util.SparseArray;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by dwj on 2016/5/30.
 */
public class Collector {

    private int mInterval;
    private int mDelay;
    private Thread mThread;
    private ScheduledExecutorService mScheduleService = null;
    private SparseArray<TraceInfo> mCache = new SparseArray<>(10);


    private Collector(int interval,int delay,Thread thread){
        this.mInterval = interval;
        this.mDelay = delay;
        this.mThread = thread;
    }

    private void initExecutor(){
        mScheduleService = Executors.newSingleThreadScheduledExecutor();
    }

    private void startCollect(){
        mScheduleService.scheduleAtFixedRate(new CollectRunnable(mThread, new CollectInter() {
            @Override
            public void onCollect(long collectTime,String info) {

            }
        }),mDelay,mInterval, TimeUnit.MILLISECONDS);
    }

    private void stopCollect(){
        if(mScheduleService != null && !mScheduleService.isShutdown()){
            mScheduleService.shutdownNow();
            mScheduleService = null;
        }
    }


    class CollectRunnable implements Runnable{

        public Thread mThread;
        private CollectInter mInter;

        public CollectRunnable(Thread thread, CollectInter inter){
            this.mThread = thread;
            this.mInter = inter;
        }

        @Override
        public void run() {
            String info = ThreadStackTraceUtil.getThreadStackInfoStr(mThread);
            if(mInter != null){
                mInter.onCollect(System.currentTimeMillis(),info);
            }
        }
    }

    interface CollectInter{
        public void onCollect(long collectTime,String info);
    }

}
