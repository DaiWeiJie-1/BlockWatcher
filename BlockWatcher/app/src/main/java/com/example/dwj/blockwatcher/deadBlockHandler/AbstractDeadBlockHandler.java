package com.example.dwj.blockwatcher.deadBlockHandler;

import com.example.dwj.blockwatcher.bean.BlockInfo;

/**
 * Description : <Content><br>
 * CreateTime : 16-6-22 下午4:45
 *
 * @author DaiWeiJie
 * @version <v1.0>
 * @Editor : DaiWeiJie
 * @ModifyTime : 16-6-22 下午4:45
 * @ModifyDescription : <Content>
 */
public abstract class AbstractDeadBlockHandler {

    protected static final long JUDGE_DEAD_TIME = 9000;

    protected long mStartTime;

    protected IDeadBlockIntercept mIntercept;

    protected BlockInfo mInfo;

    public abstract void handleDeadBlock();

    public abstract boolean isDeadBlock(long startTime,long nowTime);

    public void setStarTime(long startTime){
        mStartTime = startTime;
    }

    public void setBlockInfo(BlockInfo info){
        this.mInfo = info;
    }

    public void setIntercept(IDeadBlockIntercept intercept){
        this.mIntercept = intercept;
    }

    /**
     * if occur dead block,we will deal with it
     * @return true if dead block occured and deal with it
     */
    public boolean updateNowTimeAndDealWith(long nowTime){
       if(isDeadBlock(mStartTime,nowTime)){
           if(mIntercept != null){
               if(mInfo != null){
                   mInfo.setBlockingTime(nowTime - mStartTime);
                   mIntercept.intercept(mInfo);
               }
           }
           handleDeadBlock();
           return true;
       }else{
           return false;
       }
    }
}
