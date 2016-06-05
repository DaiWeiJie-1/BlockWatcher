package com.example.dwj.blockwatcher;

import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
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
    private CopyOnWriteArrayList<TraceInfo> mCache = new CopyOnWriteArrayList<TraceInfo>();


    public Collector(int interval,int delay,Thread thread){
        this.mInterval = interval;
        this.mDelay = delay;
        this.mThread = thread;
    }

    private void initExecutor(){
        if(mScheduleService == null){
            mScheduleService = Executors.newSingleThreadScheduledExecutor();
        }
    }

    public void startCollect(){
        stopCollect();
        initExecutor();
        clearCache();
        mScheduleService.scheduleAtFixedRate(new CollectRunnable(mThread, new CollectInter() {
            @Override
            public void onCollect(long collectTime,String[] info,String[] methods) {
                TraceInfo trace = new TraceInfo();
                trace.setOccurTime(collectTime);
                trace.setDetailInfos(info);
                trace.setInvokeMethods(methods);
                mCache.add(trace);
            }
        }),mDelay,mInterval, TimeUnit.MILLISECONDS);
    }

    public void stopCollect(){
        if(mScheduleService != null && !mScheduleService.isShutdown()){
            mScheduleService.shutdownNow();
            mScheduleService = null;
        }
    }

    private void clearCache(){
        if(mCache != null){
            mCache.clear();
        }
    }

    public BlockInfo getBlockInfo(){
        BlockInfo info = new BlockInfo();
        info.setOccurTime(System.currentTimeMillis());
        info.setTraceInfo(mCache.get(mCache.size()/2));
        return info;
    }

    public void showCacheSomething(){
        Log.d("collector","cacheSize = " + mCache.size() +" \n,mcache = " + mCache.toString());
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
            String[] tracInfo = ThreadStackTraceUtil.getThreadStackInfos(mThread);
            String[] methods = ThreadStackTraceUtil.getThreadStackMethodInvokeInfos(mThread);
            if(mInter != null){
                mInter.onCollect(System.currentTimeMillis(),tracInfo,methods);
            }
        }
    }

    interface CollectInter{
        public void onCollect(long collectTime,String[] traceInfo, String[] methodInfos);
    }

}
