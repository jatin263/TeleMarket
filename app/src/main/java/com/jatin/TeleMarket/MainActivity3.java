package com.jatin.TeleMarket;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;

import java.io.File;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;

public class MainActivity3 extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onReceive(Context context, Intent intent) {
        registerCustomTelephonyCallback(context);
    }

    @RequiresApi(Build.VERSION_CODES.S)
    class CustomTelephonyCallback extends TelephonyCallback implements TelephonyCallback.CallStateListener {
        private CallBack mCallBack;

        public CustomTelephonyCallback(CallBack callBack) {
            mCallBack = callBack;
        }

        @Override
        public void onCallStateChanged(int state) {

            mCallBack.callStateChanged(state);

        }
    }


    @RequiresApi(Build.VERSION_CODES.S)
    public void registerCustomTelephonyCallback(Context context) {
        TelephonyManager telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        telephony.registerTelephonyCallback(context.getMainExecutor(), new CustomTelephonyCallback(new CallBack() {
            @Override
            public void callStateChanged(int state) {

                int myState=state;

            }
        }));


    }

    interface CallBack {
        void callStateChanged(int state);
    }}