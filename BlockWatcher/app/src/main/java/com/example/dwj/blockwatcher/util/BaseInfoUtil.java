package com.example.dwj.blockwatcher.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dwj on 2016/6/27.
 */
public class BaseInfoUtil {

    private static final String LINE_BREAK = "\n"; //换行
    private static final String INDENT = "\t\t\t\t"; //缩进

    /**
     * [时间]
     *     时间:xxxx-xx-xx xx:xx:xx
     *
     * [设备信息]
     *     设备型号:xxxx
     *     设备唯一码:xxx
     *     设备制造厂商:xxx
     *     系统定制厂商:xxxx
     *
     * [系统信息]
     *     系统版本号:xxxx
     *
     * [应用信息]
     *     应用名称:xxx
     *     应用包名:xxxx
     *     应用版本名称:xxx
     *     应用版本号:xxxx
     *     应用签名信息:xxxx
     *
     * [进程信息]
     *     进程号:xxx
     *     线程号:xxx
     *
     */

    public static String getBaseInfo(Context context){
        StringBuilder builder = new StringBuilder();
        builder.append(getSystemTime());
        builder.append(getDeviceInfo());
        builder.append(getSystemInfo());
        try {
            builder.append(getAppInfo(context));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        builder.append(getPidInfo());

        return builder.toString();
    };

    /**
     * [时间]
     *     时间:xxxx-xx-xx xx:xx:xx
     *
     */
    public static String getSystemTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr =  format.format(new Date());
        StringBuilder builder = new StringBuilder();
        builder.append("[ Time ]");
        builder.append(LINE_BREAK);
        builder.append(INDENT);
        builder.append("Time : ");
        builder.append(timeStr);
        builder.append(LINE_BREAK);
        return builder.toString();
    }


    /**
     * [设备信息]
     *     设备型号:xxxx
     *     设备唯一码:xxx
     *     设备制造厂商:xxx
     *     系统定制厂商:xxxx
     */
    public static String getDeviceInfo(){
        //title
        StringBuilder builder = new StringBuilder();
        builder.append("[ Device ]");
        builder.append(LINE_BREAK);

        //model
        builder.append(INDENT);
        builder.append("Model : ");
        builder.append(Build.MODEL);
        builder.append(LINE_BREAK);

        //unique code
        builder.append(INDENT);
        builder.append("Unique Code : ");
        builder.append(Build.FINGERPRINT);
        builder.append(LINE_BREAK);

        //manufacturer
        builder.append(INDENT);
        builder.append("Manufacturer : ");
        builder.append(Build.MANUFACTURER);
        builder.append(LINE_BREAK);

        //system customer
        builder.append(INDENT);
        builder.append("System Customer : ");
        builder.append(Build.BRAND);
        builder.append(LINE_BREAK);

        return builder.toString();
    }


    /**
     *  [系统信息]
     *     系统版本名称:xxxx
     *     系统版本号:xxx
     *
     */
    public static String getSystemInfo(){
        StringBuilder builder = new StringBuilder();
        //title
        builder.append("[ System ]");
        builder.append(LINE_BREAK);

        //version name
        builder.append(INDENT);
        builder.append("Version Name : ");
        builder.append(Build.VERSION.RELEASE);
        builder.append(LINE_BREAK);

        //verison code
        builder.append(INDENT);
        builder.append("Version Code : ");
        builder.append(Build.VERSION.SDK_INT);
        builder.append(LINE_BREAK);

        return builder.toString();
    }


    /**
     * [应用信息]
     *     应用名称:xxx
     *     应用包名:xxxx
     *     应用版本名称:xxx
     *     应用版本号:xxxx
     *     应用签名信息:xxxx
     *
     */
    public static String getAppInfo(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);

        StringBuilder builder = new StringBuilder();
        //title
        builder.append("[ App ]");
        builder.append(LINE_BREAK);

        //app name
        builder.append(INDENT);
        builder.append("App Name : ");
        builder.append(applicationInfo.loadLabel(packageManager));
        builder.append(LINE_BREAK);

        //package name
        builder.append(INDENT);
        builder.append("Package Name : ");
        builder.append(applicationInfo.packageName);
        builder.append(LINE_BREAK);

        //version name
        builder.append(INDENT);
        builder.append("Version Name : ");
        builder.append(packageInfo.versionName);
        builder.append(LINE_BREAK);

        //version code
        builder.append(INDENT);
        builder.append("Version Code : ");
        builder.append(packageInfo.versionCode);
        builder.append(LINE_BREAK);

        //signature
        //TODO

        return builder.toString();
    }


    /**
     * [进程信息]
     *     进程号:xxx
     *     线程号:xxx
     *
     */
    public static String getPidInfo(){
        StringBuilder builder = new StringBuilder();
        //title
        builder.append("[ PidInfo ]");
        builder.append(LINE_BREAK);

        //pid
        builder.append(INDENT);
        builder.append("Pid : ");
        builder.append(Process.myPid());
        builder.append(LINE_BREAK);

        //thread
        builder.append(INDENT);
        builder.append("Thread : ");
        builder.append(Thread.currentThread().getId());
        builder.append(LINE_BREAK);

        return builder.toString();
    }
}
