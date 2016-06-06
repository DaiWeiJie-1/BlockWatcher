package com.example.dwj.blockwatcher.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import com.example.dwj.blockwatcher.BlockInfo;
import com.example.dwj.blockwatcher.R;

/**
 * Created by dwj on 2016/6/5.
 */
public class BlockNotificationManager {

    private volatile static BlockNotificationManager mInstance;

    private BlockNotificationManager(){};

    public static BlockNotificationManager getInstance(){
        if(mInstance == null){
            synchronized (BlockNotificationManager.class){
                if(mInstance == null){
                    mInstance = new BlockNotificationManager();
                }
            }
        }
        return mInstance;
    }

    public void showBlockInfoNotification(Context context, BlockInfo blockInfo){
        if(blockInfo.getTraceInfo().getUserCodeTraceWay().length < 0){
            return;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("BlockInfo");
        builder.setContentText("BlockingTime : " + String.valueOf(blockInfo.getBlockingTime()));
        builder.setShowWhen(true);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.alert);

        NotificationCompat.BigTextStyle bigTextStyleBuilder = new android.support.v4.app.NotificationCompat.BigTextStyle(builder);
        bigTextStyleBuilder.setBigContentTitle("BlockDetailInfo");
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

}
