package com.example.dwj.blockwatcher;

import android.content.Context;
import android.os.Looper;

/**
 * Created by dwj on 2016/5/29.
 */
public class LooperLogger {

    private Looper mLooper;
    private Context mContext;
    private IPrinter mIPrinter;

    public LooperLogger(Context context,IPrinter printer){
        this.mContext = context;
        this.mLooper = getMainLooper(context);
        this.mIPrinter = printer;

    }

    public Looper getMainLooper(Context context){
        if(context != null){
            return context.getMainLooper();
        }else{
            return null;
        }
    }

    public void install(){
        if(mLooper != null){
            installPrinter(mLooper,mIPrinter);
        }
    }

    private void installPrinter(Looper looper, IPrinter printer){
        if(looper != null){
            looper.setMessageLogging(printer);
        }
    }

    public void uninstall(){
        if(mLooper != null){
            installPrinter(mLooper,null);
        }
    }





}
