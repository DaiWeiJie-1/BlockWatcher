package com.example.dwj.blockwatcher.outputter;

import android.content.Context;

import com.example.dwj.blockwatcher.bean.BlockInfo;

/**
 * Created by dwj on 2016/6/27.
 */
public class FileOutPutter implements IOutputter{

    private Context mContext;
    private String mFilePath;

    public FileOutPutter(Context context, String filePath) {
        mContext = context;
        mFilePath = filePath;
    }

    @Override
    public void outPutBlockInfo(BlockInfo blockInfo) {

    }





}
