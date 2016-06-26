package com.example.dwj.blockwatcher.bean;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dwj on 2016/6/5.
 */
public class BlockInfo {

    private TraceInfo mTraceInfo;

    private long mBlockingTime;

    private long mOccurTime;

    private String mBlockEntrance;


    public TraceInfo getTraceInfo() {
        return mTraceInfo;
    }

    public long getBlockingTime() {
        return mBlockingTime;
    }

    public long getOccurTime() {
        return mOccurTime;
    }


    public void setTraceInfo(TraceInfo traceInfo) {
        this.mTraceInfo = traceInfo;
    }

    public void setBlockingTime(long blockingTime) {
        this.mBlockingTime = blockingTime;
    }

    public void setOccurTime(long occurTime) {
        this.mOccurTime = occurTime;
    }

    public String getOccurTimeStr(){
        String time = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time = format.format(new Date(getOccurTime())).toString();
        return time;
    }


    public String getBlockEntrance() {
        if(mTraceInfo != null){
            String[] userCodeTraceWay = mTraceInfo.getUserCodeTraceWay();

            if(userCodeTraceWay.length > 0){
                return userCodeTraceWay[userCodeTraceWay.length - 1];
            }
        }

        return "";
    }
}
