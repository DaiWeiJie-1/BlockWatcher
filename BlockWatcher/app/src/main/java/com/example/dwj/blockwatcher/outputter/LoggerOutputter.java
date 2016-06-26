package com.example.dwj.blockwatcher.outputter;

import android.util.Log;

import com.example.dwj.blockwatcher.bean.BlockInfo;

/**
 * Created by dwj on 2016/6/26.
 */
public class LoggerOutputter implements IOutputter {

    private final static String STRING_CHANGE_LINE = "\n";
    private final static String TAG = "BlockWatcher";

    @Override
    public void outPutBlockInfo(BlockInfo blockInfo) {
        Log.e(TAG,getInfoStr(blockInfo));
    }

    private String getInfoStr(BlockInfo info){
        if(info == null){
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Block occured : ");
        builder.append(STRING_CHANGE_LINE);
        builder.append("occured time :");
        builder.append(info.getOccurTimeStr());
        builder.append(STRING_CHANGE_LINE);
        builder.append("Block trace:");
        builder.append(STRING_CHANGE_LINE);
        for(String detail : info.getTraceInfo().getDetailInfos()){
            builder.append(detail);
            builder.append(STRING_CHANGE_LINE);
        }

        return builder.toString();
    }
}
