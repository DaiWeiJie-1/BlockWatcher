package com.example.dwj.blockwatcher.outputter;

import android.content.Context;

import com.example.dwj.blockwatcher.bean.BlockInfo;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 2016/7/10.
 */
public class OutputterChains {
    private CopyOnWriteArrayList<AbstractOutputter> mChains;
    private static volatile OutputterChains mInstance;

    private OutputterChains(){};

    public static OutputterChains getInstance(){
        if(mInstance == null){
            synchronized (OutputterChains.class){
                if(mInstance == null){
                    mInstance = new OutputterChains();
                }
            }
        }
        return mInstance;
    }

    public void clearChains(){
        if(mChains != null){
            mChains.clear();
        }
    }

    public <T extends AbstractOutputter> void addItem(T outputter){
        if(mChains == null){
            mChains = new CopyOnWriteArrayList<AbstractOutputter>();
        }
        mChains.add(outputter);
    }

    public <T extends AbstractOutputter> void removeItem(T outputter){
        if(mChains != null){
            mChains.remove(outputter);
        }
    }

    public void deliver(Context context, BlockInfo info){
        if(mChains != null){
            for(AbstractOutputter outputter : mChains){
                outputter.handle(context,info);
            }
        }
    }

}
