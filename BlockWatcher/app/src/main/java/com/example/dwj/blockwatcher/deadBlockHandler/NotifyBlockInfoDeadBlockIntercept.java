package com.example.dwj.blockwatcher.deadBlockHandler;

import android.content.Context;

import com.example.dwj.blockwatcher.bean.BlockInfo;

/**
 * Description : <Content><br>
 * CreateTime : 16-6-22 下午6:02
 *
 * @author DaiWeiJie
 * @version <v1.0>
 * @Editor : DaiWeiJie
 * @ModifyTime : 16-6-22 下午6:02
 * @ModifyDescription : <Content>
 */
public class NotifyBlockInfoDeadBlockIntercept implements IDeadBlockIntercept {

    private Context mContext;
    private BlockInfo mBlockInfo;

    public NotifyBlockInfoDeadBlockIntercept(Context context) {
        this.mContext = context;
    }

    public void setBlockInfo(BlockInfo blockInfo){
         this.mBlockInfo = blockInfo;
    }

    @Override
    public void intercept() {
        BlockNotificationManager.getInstance().showBlockInfoNotification(mContext,mBlockInfo);
    }
}
