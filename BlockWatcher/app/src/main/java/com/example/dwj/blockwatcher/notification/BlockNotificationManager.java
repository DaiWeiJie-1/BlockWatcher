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
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("BlockInfo");
        builder.setContentText(blockInfo.getBlockEntrance());
        builder.setShowWhen(true);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.alert);

        NotificationCompat.InboxStyle inboxStyle = new android.support.v4.app.NotificationCompat.InboxStyle(builder);
        String[] traceInfos = blockInfo.getTraceInfo().getUserCodeTraceWay();
        for(int i = traceInfos.length - 1; i >= 0; i --){
            inboxStyle.addLine(traceInfos[i]);
        }
        Notification notification = inboxStyle.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);

    }

}
