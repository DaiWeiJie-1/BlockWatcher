package com.example.dwj.blockwatcher.deadBlockHandler;

import android.content.Context;

import com.example.dwj.blockwatcher.bean.BlockInfo;
import com.example.dwj.blockwatcher.outputter.OutputterChains;

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
public class OutPutBlockInfoDeadBlockIntercept implements IDeadBlockIntercept {

    private Context mContext;

    public OutPutBlockInfoDeadBlockIntercept(Context context) {
        this.mContext = context;
    }

    @Override
    public void intercept(BlockInfo info) {
        if(info == null){
            return;
        }
        OutputterChains.getInstance().deliver(mContext,info);
    }
}
