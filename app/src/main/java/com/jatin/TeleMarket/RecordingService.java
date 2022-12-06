package com.jatin.TeleMarket;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executor;

public class RecordingService extends Service {
    private MediaRecorder rec;
    private boolean recStart;
    private File file;
    String path = "data/0/tekk";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RECORDINGS);
        }
        Date date = new Date();
        CharSequence sdf = DateFormat.format("MM-dd-yy-hh-mm-ss",date.getTime());
        rec = new MediaRecorder();
        rec.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
        rec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        rec.setOutputFile(file.getAbsoluteFile()+"/"+sdf+"rec.3gp");
        rec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        TelephonyManager manager = (TelephonyManager) getApplicationContext().getSystemService(TELEPHONY_SERVICE);
//        TelephonyCallback.CallStateListener(manager.getCallState());
        manager.listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String phoneNumber) {
               // super.onCallStateChanged(state, phoneNumber){
                    if (TelephonyManager.CALL_STATE_IDLE==state && rec == null){
                        rec.stop();
                        rec.reset();
                        rec.release();
                        recStart=false;
                        stopSelf();
                    }else if(TelephonyManager.CALL_STATE_OFFHOOK==state){
                        try {
                            rec.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        rec.start();
                        recStart=true;
                    }
                }

        },PhoneStateListener.LISTEN_CALL_STATE);

        return START_STICKY;
    }
}
