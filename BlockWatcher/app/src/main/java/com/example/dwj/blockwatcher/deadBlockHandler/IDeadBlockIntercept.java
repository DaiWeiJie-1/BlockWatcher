package com.example.dwj.blockwatcher.deadBlockHandler;

import com.example.dwj.blockwatcher.bean.BlockInfo;

/**
 * Description : 死阻塞前拦截器
 * CreateTime : 16-6-22 下午5:54
 *
 * @author DaiWeiJie
 * @version <v1.0>
 * @Editor : DaiWeiJie
 * @ModifyTime : 16-6-22 下午5:54
 * @ModifyDescription : <Content>
 */
public interface IDeadBlockIntercept {
    public void intercept(BlockInfo info);
}
