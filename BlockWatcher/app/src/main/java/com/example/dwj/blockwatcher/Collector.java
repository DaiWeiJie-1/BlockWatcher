package com.example.dwj.blockwatcher;

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
    private ScheduledExecutorService mScheduleService = null;


    private Collector(int interval,int delay){
        this.mInterval = interval;
        this.mDelay = delay;
    }

    private void initExecutor(){
        mScheduleService = Executors.newSingleThreadScheduledExecutor();
    }

    private void startCollect(){
        mScheduleService.scheduleAtFixedRate(null,mDelay,mInterval, TimeUnit.MILLISECONDS);
    }

    private void stopCollect(){
        if(mScheduleService != null && !mScheduleService.isShutdown()){
            mScheduleService.shutdownNow();
            mScheduleService = null;
        }
    }


    class CollecRunnable implements Runnable{

        public Thread mThread;

        public CollecRunnable(Thread thread){
            this.mThread = thread;
        }

        @Override
        public void run() {
            String info = ThreadStackTraceUtil.getThreadStackInfoStr(mThread);
        }
    }

}
