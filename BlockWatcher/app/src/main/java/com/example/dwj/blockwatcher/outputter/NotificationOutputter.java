package com.example.dwj.blockwatcher.outputter;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import com.example.dwj.blockwatcher.R;
import com.example.dwj.blockwatcher.bean.BlockInfo;
import com.example.dwj.blockwatcher.outputter.IOutputter;

import java.lang.ref.WeakReference;

/**
 * 通知输出类
 * Created by dwj on 2016/6/26.
 */
public class NotificationOutputter implements IOutputter {

    private final static String CONTENT_TITLE = "BlockInfo";
    private final static String CONTENT_TEXT_PART = "BlockingTime : ";
    private final static String BIG_CONTENT_TITLE = "BlockDetailInfo";

    private WeakReference<Context> mWeakContextRef = null;

    public NotificationOutputter(Context context){
        mWeakContextRef = new WeakReference<Context>(context);
    };

    public void showBlockInfoNotification(Context context, BlockInfo blockInfo){
        if(context == null || blockInfo == null){
            return;
        }
        if(blockInfo.getTraceInfo().getUserCodeTraceWay().length < 0){
            return;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(CONTENT_TITLE);
        builder.setContentText(CONTENT_TEXT_PART + String.valueOf(blockInfo.getBlockingTime()));
        builder.setShowWhen(true);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.alert);

        NotificationCompat.BigTextStyle bigTextStyleBuilder = new android.support.v4.app.NotificationCompat.BigTextStyle(builder);
        bigTextStyleBuilder.setBigContentTitle(BIG_CONTENT_TITLE);
        bigTextStyleBuilder.setSummaryText(blockInfo.getBlockEntrance());
        String[] codeWay = blockInfo.getTraceInfo().getUserCodeTraceWay();
        StringBuilder strBuilder = new StringBuilder();
        for(int i = codeWay.length - 1; i >= 0; i --){
            strBuilder.append(codeWay[i]);
            strBuilder.append("\n");
        }

        strBuilder.deleteCharAt(strBuilder.length() - 1);
        bigTextStyleBuilder.bigText(strBuilder.toString());
        Notification notification = bigTextStyleBuilder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);

    }


    @Override
    public void outPutBlockInfo(BlockInfo blockInfo) {
        if(mWeakContextRef != null && mWeakContextRef.get() != null){
            showBlockInfoNotification(mWeakContextRef.get(),blockInfo);
        }
    }
}
