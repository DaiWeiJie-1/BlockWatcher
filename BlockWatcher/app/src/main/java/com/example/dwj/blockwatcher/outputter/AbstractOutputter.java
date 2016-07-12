package com.example.dwj.blockwatcher.outputter;

import android.content.Context;

import com.example.dwj.blockwatcher.bean.BlockInfo;
import com.example.dwj.blockwatcher.util.BaseInfoUtil;

/**
 * Created by dwj on 2016/6/26.
 */
public abstract class AbstractOutputter {

    protected abstract void outPutBlockInfo(String blockInfoStr);

    public void handle(Context context,BlockInfo info){
        String blockInfoStr = getBlockInfoStr(info,context);
        outPutBlockInfo(blockInfoStr);
    }

    protected String getBlockInfoStr(BlockInfo info,Context context){
        if(info == null){
            return null;
        }
        String baseInfoStr = BaseInfoUtil.getBaseInfo(context);
        return baseInfoStr + info.toString() + "\n\n\n\n";
    }

}
