package com.gsttarun.applocklibrary;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.content.res.TypedArray;
import android.view.ViewGroup;

/**
 *
 */
public class AppLockView extends ViewGroup {
    private int mKeyType = 0;

    public AppLockView(Context context) {
        super(context);
    }

    public AppLockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AppLockView(Context context, @Nullable AttributeSet attrs,
                       int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray
                = getContext().obtainStyledAttributes(attrs, R.styleable.AppLockView);
        mKeyType = typedArray.getInt(R.styleable.AppLockView_lockType, mKeyType);
        typedArray.recycle();
        View view;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        switch (mKeyType) {
            default:
            case LockKeyType.PATTERN:
                view = inflater.inflate(R.layout.activity_lock,this,true);
                break;
            case LockKeyType.NUMERIC:
                view = inflater.inflate(R.layout.activity_lock,this,true);
                break;
            case LockKeyType.ALPHANUMERIC:
                view = inflater.inflate(R.layout.activity_lock,this,true);
                break;
            case LockKeyType.FINGERPRINT:
                view = inflater.inflate(R.layout.activity_lock,this,true);
                break;
        }
        addView(view);
    }
}
