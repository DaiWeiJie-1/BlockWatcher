package com.example.dwj.blockwatcher.deadBlockHandler;

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

    protected long mStartTime;

    protected IDeadBlockIntercept mIntercept;

    public abstract void handleDeadBlock();

    public abstract boolean isDeadBlock(long startTime,long nowTime);

    public void setStarTime(long startTime){
        mStartTime = startTime;
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
               mIntercept.intercept();
           }
           handleDeadBlock();
           return true;
       }else{
           return false;
       }
    }
}
