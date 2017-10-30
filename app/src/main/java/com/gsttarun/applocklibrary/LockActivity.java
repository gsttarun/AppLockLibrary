package com.gsttarun.applocklibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;
import com.andrognito.rxpatternlockview.RxPatternLockView;
import com.andrognito.rxpatternlockview.events.PatternLockCompleteEvent;
import com.andrognito.rxpatternlockview.events.PatternLockCompoundEvent;
import com.github.ajalt.reprint.core.AuthenticationFailureReason;
import com.github.ajalt.reprint.core.AuthenticationListener;
import com.github.ajalt.reprint.core.Reprint;

import java.util.List;

import io.reactivex.functions.Consumer;

public class LockActivity extends AppCompatActivity {

    private AuthenticationListener authenticationListener = new AuthenticationListener() {
        public void onSuccess(int moduleTag) {
            closeThisActivity();
        }

        public void onFailure(AuthenticationFailureReason failureReason, boolean fatal,
                              CharSequence errorMessage, int moduleTag, int errorCode) {
            Log.d(getClass().getName(), failureReason.name());
        }
    };
    private boolean isFingerPrintLockEnabled;
    private PatternLockView mPatternLockView;
    private View mFingerprintImageView;
    private String correctPassword;

    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            Log.d(getClass().getName(), "Pattern drawing started");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            Log.d(getClass().getName(), "Pattern progress: " +
                    PatternLockUtils.patternToString(mPatternLockView, progressPattern));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            String patternToString = PatternLockUtils.patternToString(mPatternLockView, pattern);
            Log.d(getClass().getName(), "Pattern complete: " +
                    patternToString);

            if (patternToString.contentEquals(correctPassword)) {
                mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                Reprint.cancelAuthentication();
                closeThisActivity();
            }
            else
                mPatternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
        }
    };

    private void closeThisActivity() {
        AppLockApplication lockApplication = (AppLockApplication) getApplication();
        lockApplication.wasInBackground = false;
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lock);

        initVariables();

        initViews();

        setUpPatternLock();

        startFingerprintAuthentication();

//        setUpObservables();
    }

    private void initVariables() {
        isFingerPrintLockEnabled = AppLockSharedPreferences.isFingerprintLockEnabled(this);
        correctPassword = AppLockSharedPreferences.getPasscode(this);
        Log.d(getClass().getName(), "isFingerPrintLockEnabled :" + isFingerPrintLockEnabled);
    }

    private void startFingerprintAuthentication() {
        if (isFingerPrintLockEnabled) {
            Reprint.authenticate(authenticationListener);
        }
    }

    private void setUpObservables() {
        RxPatternLockView.patternComplete(mPatternLockView)
                .subscribe(new Consumer<PatternLockCompleteEvent>() {
                    @Override
                    public void accept(PatternLockCompleteEvent patternLockCompleteEvent) throws Exception {
                        Log.d(getClass().getName(), "Complete: " + patternLockCompleteEvent.getPattern().toString());
                    }
                });

        RxPatternLockView.patternChanges(mPatternLockView)
                .subscribe(new Consumer<PatternLockCompoundEvent>() {
                    @Override
                    public void accept(PatternLockCompoundEvent event) throws Exception {
                        if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_STARTED) {
                            Log.d(getClass().getName(), "Pattern drawing started");
                        }
                        else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_PROGRESS) {
                            Log.d(getClass().getName(), "Pattern progress: " +
                                    PatternLockUtils.patternToString(mPatternLockView, event.getPattern()));
                        }
                        else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_COMPLETE) {
                            Log.d(getClass().getName(), "Pattern complete: " +
                                    PatternLockUtils.patternToString(mPatternLockView, event.getPattern()));
                        }
                        else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_CLEARED) {
                            Log.d(getClass().getName(), "Pattern has been cleared");
                        }
                    }
                });
    }

    private void initViews() {
        mFingerprintImageView = (View) findViewById(R.id.fingerprint_image);

        if (isFingerPrintLockEnabled) {
            mFingerprintImageView.setVisibility(View.VISIBLE);
        }
        else {
            mFingerprintImageView.setVisibility(View.GONE);
        }
        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
    }

    private void setUpPatternLock() {
        mPatternLockView.setDotCount(3);
        mPatternLockView.setDotNormalSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_size));
        mPatternLockView.setDotSelectedSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_selected_size));
        mPatternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_path_width));
        mPatternLockView.setAspectRatioEnabled(true);
        mPatternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);
        mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
        mPatternLockView.setDotAnimationDuration(150);
        mPatternLockView.setPathEndAnimationDuration(100);
        mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(this, R.color.green));
        mPatternLockView.setNormalStateColor(ResourceUtils.getColor(this, R.color.white));
        mPatternLockView.setWrongStateColor(ResourceUtils.getColor(this, R.color.pomegranate));
        mPatternLockView.setInStealthMode(false);
        mPatternLockView.setTactileFeedbackEnabled(true);
        mPatternLockView.setInputEnabled(true);

        mPatternLockView.addPatternLockListener(mPatternLockViewListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppLockApplication myApp = (AppLockApplication) this.getApplication();
        myApp.wasInBackground = true;
        minimizeApp();
    }


    public void minimizeApp() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(getClass().getName(), "new Intent");
        super.onNewIntent(intent);
    }
}
