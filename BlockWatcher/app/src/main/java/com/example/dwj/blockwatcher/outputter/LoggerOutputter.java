package com.example.dwj.blockwatcher.outputter;

import android.util.Log;

/**
 * Created by dwj on 2016/6/26.
 */
public class LoggerOutputter extends AbstractOutputter {

    private final static String TAG = "BlockWatcher";

    @Override
    protected void outPutBlockInfo(String blockInfoStr) {
        Log.e(TAG,blockInfoStr);
    }

}
