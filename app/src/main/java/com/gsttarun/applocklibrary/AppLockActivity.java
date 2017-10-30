package com.gsttarun.applocklibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class AppLockActivity extends AppCompatActivity {
    @Override
    public void onResume() {
        super.onResume();

        AppLockApplication myApp = (AppLockApplication) this.getApplication();

        if (myApp.wasInBackground) {
            if (AppLockSharedPreferences.isLockEnabled(this)) {
                myApp.stopActivityTransitionTimer();
                Intent intent = new Intent(getApplicationContext(), LockActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        }

        myApp.stopActivityTransitionTimer();
    }


    @Override
    public void onPause() {
        super.onPause();
        ((AppLockApplication) this.getApplication()).startActivityTransitionTimer();
/*        Intent intent = new Intent(getApplicationContext(), LockActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);*/
    }
}
