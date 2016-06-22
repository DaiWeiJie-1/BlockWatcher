package com.example.dwj.demo;

import android.app.Application;

import com.example.dwj.blockwatcher.BlockWatcher;

/**
 * Created by dwj on 2016/6/5.
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        BlockWatcher.watch(getApplicationContext()).install();

    }
}
