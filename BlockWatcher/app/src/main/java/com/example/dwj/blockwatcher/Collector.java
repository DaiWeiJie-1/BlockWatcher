package com.example.dwj.blockwatcher;

import android.content.Context;
import android.util.Log;

import com.example.dwj.blockwatcher.bean.BlockInfo;
import com.example.dwj.blockwatcher.bean.TraceInfo;
import com.example.dwj.blockwatcher.deadBlockHandler.AbstractDeadBlockHandler;
import com.example.dwj.blockwatcher.deadBlockHandler.IDeadBlockIntercept;
import com.example.dwj.blockwatcher.deadBlockHandler.OutPutBlockInfoDeadBlockIntercept;
import com.example.dwj.blockwatcher.deadBlockHandler.RestartDeadBlockHandler;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by dwj on 2016/5/30.
 */
public class Collector {

    private Context mContext;
    private int mInterval;
    private int mDelay;
    private Thread mThread;
    private ScheduledExecutorService mScheduleService = null;
    private AbstractDeadBlockHandler mDeadBlockHandler = null;
    private IDeadBlockIntercept mDeadBlockIntercept = null;
    private CopyOnWriteArrayList<TraceInfo> mCache = new CopyOnWriteArrayList<TraceInfo>();
    private long mStartCollectTime = 0;
    private long mEndCollectTime = 0;

    public Collector(Context context,int interval, int delay, Thread thread){
        this.mContext  = context;
        this.mInterval = interval;
        this.mDelay = delay;
        this.mThread = thread;
        mDeadBlockHandler = new RestartDeadBlockHandler(mContext);
        mDeadBlockIntercept = new OutPutBlockInfoDeadBlockIntercept(mContext);
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
        mStartCollectTime = System.currentTimeMillis();
        mDeadBlockHandler.setStarTime(mStartCollectTime);
        mScheduleService.scheduleAtFixedRate(new CollectRunnable(mThread, new CollectInter() {
            @Override
            public void onCollect(long collectTime,String[] info,String[] methods) {
                collectTraceInfo(collectTime,info,methods);
                if(mDeadBlockHandler != null){
                    boolean deadBlock = mDeadBlockHandler.updateNowTimeAndDealWith(System.currentTimeMillis());
                    if(deadBlock){
                        stopCollect();
                    }
                }
            }
        }),mDelay,mInterval, TimeUnit.MILLISECONDS);
    }

    private void collectTraceInfo(long collectTime,String[] info,String[] methods){
        TraceInfo trace = new TraceInfo();
        trace.setOccurTime(collectTime);
        trace.setDetailInfos(info);
        trace.setInvokeMethods(methods);
        mCache.add(trace);
    }

    public void stopCollect(){
        if(mScheduleService != null && !mScheduleService.isShutdown()){
            mScheduleService.shutdownNow();
            mScheduleService = null;
            mEndCollectTime = System.currentTimeMillis();
        }
    }

    private void clearCache(){
        if(mCache != null){
            mCache.clear();
        }
        mStartCollectTime = 0;
        mEndCollectTime = 0;
    }

    public long getBlockingTime(){
        return mEndCollectTime - mStartCollectTime;
    }

    public BlockInfo getBlockInfo(){
        BlockInfo info = new BlockInfo();
        info.setOccurTime(System.currentTimeMillis());
        info.setTraceInfo(mCache.get(mCache.size()/2));
        info.setBlockingTime(getBlockingTime());
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
