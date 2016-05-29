package com.example.dwj.blockwatcher;

/**
 * Created by dwj on 2016/5/29.
 */
public class ThreadStackTraceUtil {

    public static String getThreadStackInfoStr(Thread thd){
        StackTraceElement[] stackTrace = thd.getStackTrace();
        StringBuilder strBuilder = new StringBuilder();
        for(StackTraceElement element : stackTrace){
            strBuilder.append(element.toString());
            strBuilder.append("\n");
        }

        return strBuilder.toString();
    }
}
