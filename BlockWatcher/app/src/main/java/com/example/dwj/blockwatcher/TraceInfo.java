package com.example.dwj.blockwatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dwj on 2016/5/30.
 */
public class TraceInfo {

    private static final String[] FILTER_PACKAGE_STARTSTR = {"java.","android.","dalvik.","com.android."};

    private long occurTime;
    private String[] detailInfos;
    private String[] invokeMethods;


    public void setOccurTime(long occurTime) {
        this.occurTime = occurTime;
    }

    public void setDetailInfos(String[] detailInfos) {
        this.detailInfos = detailInfos;
    }

    public void setInvokeMethods(String[] invokeMethods) {
        this.invokeMethods = invokeMethods;
    }

    public long getOccurTime() {
        return occurTime;
    }

    public String[] getDetailInfos() {
        return detailInfos;
    }

    public String[] getInvokeMethods() {
        return invokeMethods;
    }

    public String getInvokeMethodTraceLine(){
        StringBuilder builder = new StringBuilder("Method Line: \n");

        if(invokeMethods.length > 0){
            for(String info : invokeMethods){
                builder.append("[");
                builder.append(info);
                builder.append("]");
                builder.append(" -> ");
            }
            builder.delete(builder.length() - 5,builder.length());
        }
        return builder.toString();
    }


    public String[] getUserCodeTraceWay(){
        return filterTraceInfo(detailInfos);
    }

    public String[] filterTraceInfo(String[] traceInfos){
        List<String> list = new ArrayList<>();
        int length = traceInfos.length;
        for(int i = 0; i < traceInfos.length; i ++){
            String traceInfo = traceInfos[i];
            if(isStartWithFilterPackageStart(traceInfo)){
                continue;
            }else{
                list.add(traceInfo);
            }
        }

        return list.toArray(new String[list.size()]);
    }

    private boolean isStartWithFilterPackageStart(String trace){
        boolean isStartWith = false;
        for(String filterStr : FILTER_PACKAGE_STARTSTR){
            if(trace.startsWith(filterStr)){
                isStartWith = true;
                break;
            }
        }

        return isStartWith;
    }

    @Override
    public String toString() {
        return "[TraceInfo: occurTime = " + occurTime + ";\n detailInfo = " + Arrays.toString(getUserCodeTraceWay())
                + ";\n invokeMethods :" + Arrays.toString(invokeMethods) + "]" ;
    }
}
