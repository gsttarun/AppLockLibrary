package com.gsttarun.applocklibrary;

import android.content.Context;
import android.content.SharedPreferences;

public class AppLockSharedPreferences {

    private static final String PREFERENCES_NAME = "lock";
    private static final String PATTERN = "pattern";
    private static final String PASSCODE = "passcode";
    private static final String IS_LOCK_ENABLED = "isLockEnabled";
    private static final String IS_FINGERPRINT_LOCK_ENABLED = "isFingerprintLockEnabled";


    public static void savePattern(Context context, String pattern) {
        try {
            SharedPreferences mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            prefsEditor.putString(PATTERN, pattern);
            prefsEditor.apply();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void savePasscode(Context context, String passcode) {
        try {
            SharedPreferences mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            prefsEditor.putString(PASSCODE, passcode);
            prefsEditor.apply();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void saveLockStatus(Context context, boolean isLockEnabled) {

        try {
            SharedPreferences mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            prefsEditor.putBoolean(IS_LOCK_ENABLED, isLockEnabled);
            prefsEditor.apply();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void saveFingerprintLockStatus(Context context, boolean isFingerprintLockEnabled) {

        try {
            SharedPreferences mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            prefsEditor.putBoolean(IS_FINGERPRINT_LOCK_ENABLED, isFingerprintLockEnabled);
            prefsEditor.apply();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean isLockEnabled(Context context) {

        try {
            SharedPreferences mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            return mPrefs.getBoolean(IS_LOCK_ENABLED, false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isFingerprintLockEnabled(Context context) {

        try {
            SharedPreferences mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            return mPrefs.getBoolean(IS_FINGERPRINT_LOCK_ENABLED, false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getPattern(Context context) {

        try {
            SharedPreferences mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            return mPrefs.getString(PATTERN, "");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getPasscode(Context context) {

        try {
            SharedPreferences mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            return mPrefs.getString(PASSCODE, "");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
