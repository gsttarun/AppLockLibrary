package com.gsttarun.applocklibrary;

import android.app.Application;
import android.os.Handler;

import com.github.ajalt.reprint.core.Reprint;

import java.util.TimerTask;


public class AppLockApplication extends Application {

    private final long MAX_ACTIVITY_TRANSITION_TIME_MS = 2000;
    public boolean wasInBackground = true;
    private TimerTask mActivityTransitionTimerTask = new TimerTask() {
        public void run() {
            AppLockApplication.this.wasInBackground = true;
        }
    };
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        Reprint.initialize(this);
    }

    public void startActivityTransitionTimer() {

        if (handler != null) {
            handler.removeCallbacks(mActivityTransitionTimerTask);
        }

        handler = new Handler();
        handler.postDelayed(mActivityTransitionTimerTask, MAX_ACTIVITY_TRANSITION_TIME_MS);
    }

    public void stopActivityTransitionTimer() {
        if (this.mActivityTransitionTimerTask != null) {
            this.mActivityTransitionTimerTask.cancel();
        }

        if (handler != null) {
            handler.removeCallbacks(mActivityTransitionTimerTask);
        }

        this.wasInBackground = false;
    }


}
