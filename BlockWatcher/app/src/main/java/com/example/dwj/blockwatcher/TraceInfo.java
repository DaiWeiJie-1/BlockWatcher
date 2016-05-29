package com.example.dwj.blockwatcher;

/**
 * Created by dwj on 2016/5/30.
 */
public class TraceInfo {

    private long occurTime;
    private String detailInfo;
    private String[] invokeMethods;


    public void setOccurTime(long occurTime) {
        this.occurTime = occurTime;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public void setInvokeMethods(String[] invokeMethods) {
        this.invokeMethods = invokeMethods;
    }

    public long getOccurTime() {
        return occurTime;
    }

    public String getDetailInfo() {
        return detailInfo;
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
}
