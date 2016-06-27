package com.pistolcaffe.lib.pistolpermission.eventbus;

import com.squareup.otto.Bus;

public class PistolBusProvider {
    private static Bus mInstance;

    private PistolBusProvider() {

    }

    public static Bus getBus() {
        if (mInstance == null) {
            mInstance = new Bus();
        }
        return mInstance;
    }
}