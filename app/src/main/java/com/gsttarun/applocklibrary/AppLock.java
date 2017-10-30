package com.gsttarun.applocklibrary;

import android.content.Context;

public class AppLock {

    private Context context;

    public AppLock(Context context) {
        this.context = context;
    }

    public boolean createNewLockKey(LockKeyType lockKeyType) {
        return false;
    }

    public boolean delete(String keyName) {
        return false;
    }

}
