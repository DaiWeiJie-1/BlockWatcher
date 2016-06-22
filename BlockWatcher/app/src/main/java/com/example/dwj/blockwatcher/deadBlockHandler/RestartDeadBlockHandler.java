package com.example.dwj.blockwatcher.deadBlockHandler;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import com.example.dwj.demo.MainActivity;

/**
 * Description : <Content><br>
 * CreateTime : 16-6-22 下午4:46
 *
 * @author DaiWeiJie
 * @version <v1.0>
 * @Editor : DaiWeiJie
 * @ModifyTime : 16-6-22 下午4:46
 * @ModifyDescription : <Content>
 */
public class RestartDeadBlockHandler extends AbstractDeadBlockHandler{

    private Context mContext;

    private static final long JUDGE_DEAD_TIME = 4000;

    public RestartDeadBlockHandler(Context context){
        mContext = context;
    }

    @Override
    public void handleDeadBlock() {
        restartApp();
    }


    private void restartApp(){
        if(mContext != null){
            Intent it = new Intent(mContext,MainActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            it.addCategory(Intent.CATEGORY_LAUNCHER);
            it.setAction(Intent.ACTION_MAIN);
            mContext.startActivity(it);
        }
        Process.killProcess(Process.myPid());
    }



    @Override
    public boolean isDeadBlock(long startTime, long nowTime) {
        if(nowTime - startTime > JUDGE_DEAD_TIME){
            Log.d("collect","nowTime = " + nowTime + "; startTime = " + startTime);
            return true;
        }else{
            return false;
        }
    }
}
