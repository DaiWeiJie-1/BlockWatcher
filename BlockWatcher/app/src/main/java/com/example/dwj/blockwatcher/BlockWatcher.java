package com.example.dwj.blockwatcher;

import android.content.Context;
import android.os.Environment;

import com.example.dwj.blockwatcher.outputter.FileOutPutter;
import com.example.dwj.blockwatcher.outputter.LoggerOutputter;
import com.example.dwj.blockwatcher.outputter.NotificationOutputter;
import com.example.dwj.blockwatcher.outputter.OutputterChains;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dwj on 2016/5/29.
 */
public class BlockWatcher {

    private final static int THRESHOLD_TIME = 2000;

    private static WeakReference<Context> contextWeakRef;
    private static volatile BlockWatcher mInstance = null;
    private static LooperLogger mLooperLogger = null;

    public static BlockWatcher watch(Context context){
        if(mInstance == null){
            synchronized (BlockWatcher.class){
                mInstance = new BlockWatcher();
            }
        }

        if(contextWeakRef != null){
            contextWeakRef.clear();
            contextWeakRef = null;
        }

        contextWeakRef = new WeakReference<Context>(context);
        return mInstance;
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
            mLooperLogger = new LooperLogger(contextWeakRef.get(), new BlockHandler(contextWeakRef.get(),THRESHOLD_TIME,Thread.currentThread()));
            mLooperLogger.install();
            OutputterChains.getInstance().addItem(new LoggerOutputter());
            OutputterChains.getInstance().addItem(new NotificationOutputter());
            OutputterChains.getInstance().addItem(new FileOutPutter(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + contextWeakRef.get().getPackageName() + File.separator
                    + "BlockWatch",new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime())));
        }
    }

}
