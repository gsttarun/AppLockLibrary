package com.gsttarun.applocklibrary;

/**
 * Created by PurpleTalk Inc. on 31/10/17.
 */

public interface OnValidationCheckListener {
    void onMatched();

    void onMisMatched();

    void onTryAfterSomeTime();
}
