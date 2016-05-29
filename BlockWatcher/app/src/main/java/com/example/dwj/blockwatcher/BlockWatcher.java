package com.example.dwj.blockwatcher;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by dwj on 2016/5/29.
 */
public class BlockWatcher {

    private final static int THRESHOLD_TIME = 300;

    private static WeakReference<Context> contextWeakRef;
    private static volatile BlockWatcher mContext = null;
    private static LooperLogger mLooperLogger = null;

    private BlockWatcher(){};

    public static BlockWatcher watch(Context context){
        if(mContext == null){
            synchronized (BlockWatcher.class){
                mContext = new BlockWatcher();
            }
        }

        if(contextWeakRef != null){
            contextWeakRef.clear();
            contextWeakRef = null;
        }

        contextWeakRef = new WeakReference<Context>(context);
        return mContext;
    }

    public static void unWatch(Context context){
        if(mLooperLogger != null) {
            mLooperLogger.uninstall();
            mLooperLogger = null;
        }

        if(contextWeakRef != null && contextWeakRef.get() != null && contextWeakRef.get() == context){
            contextWeakRef.clear();
            contextWeakRef = null;
        }
    }

    public void install(){
        if(contextWeakRef != null && contextWeakRef.get() != null){
            mLooperLogger = new LooperLogger(contextWeakRef.get(), new BlockHandler(THRESHOLD_TIME,Thread.currentThread()));
            mLooperLogger.install();
        }
    }

}
