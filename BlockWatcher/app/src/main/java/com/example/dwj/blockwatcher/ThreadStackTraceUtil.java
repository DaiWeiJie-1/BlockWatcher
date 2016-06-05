package com.example.dwj.blockwatcher;

/**
 * Created by dwj on 2016/5/29.
 */
public class ThreadStackTraceUtil {

    public static String[] getThreadStackInfos(Thread thd){
        StackTraceElement[] stackTrace = thd.getStackTrace();
        int length = stackTrace.length;
        String[] traces = new String[length];
        for(int i = 0; i < length ; i ++){
            traces[i] = stackTrace[i].toString();
        }

        return traces;
    }

    public static String[] getThreadStackMethodInvokeInfos(Thread thd){
        StackTraceElement[] stackTrace = thd.getStackTrace();
        int length = stackTrace.length;
        String[] methods = new String[length];
        for(int i = 0; i < length ; i ++){
            methods[i] = stackTrace[i].getMethodName();
        }
        return methods;
    }
}
