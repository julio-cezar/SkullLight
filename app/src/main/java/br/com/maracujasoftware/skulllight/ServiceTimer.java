package br.com.maracujasoftware.skulllight;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/**
 * Created by julio on 21/08/2016.
 */
public class ServiceTimer extends Service implements TimeLeftListener {
    MediaPlayer mpSound;
    CounterClass timer;
    int  alarmSound;
    String hms;

    LocalBroadcastManager broadcaster;

    static final public String SKULL_RESULT = "br.com.maracujasoftware.skulllight.REQUEST_PROCESSED";

    static final public String SKULL_TIMER_MESSAGE = "br.com.maracujasoftware.skulllight.SKULL_TIMER_MESSAGE";

    private Controller controller = new Controller();

    public class Controller extends Binder {
        public TimeLeftListener getTimeLeftListener(){
            return(ServiceTimer.this);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("ServiceLog", "onBind()");
        return controller;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i("ServiceLog", "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ServiceLog", "onStartCommand()");

        broadcaster = LocalBroadcastManager.getInstance(this);

        if (intent !=null){
            if(intent.getExtras()!=null){
                alarmSound = (int)intent.getExtras().get("mAlarmSound");

            }
        }
        setTimerThread();
        return super.onStartCommand(intent, flags, startId);
    }

    public void sendResult(String message) {
        Intent intent = new Intent(SKULL_RESULT);
        if(message != null)
            intent.putExtra(SKULL_TIMER_MESSAGE, message);
        broadcaster.sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("ServiceLog", "onDestroy()");
        if (mpSound != null) {
            //if (mpSound.isPlaying()) {
            mpSound.release();
            //}
        }
        if(timer!=null) timer.cancel();
    }

    public void setTimerThread(){
        Log.i("ServiceLog", "setTimerThread()");
        if(timer!=null) timer.cancel();
        timer = new CounterClass(9000, 1000);
        timer.start();
    }

    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            //tvTime.setText(hms);
            sendResult(hms);
        }

        @Override
        public void onFinish() {
           //  Toast.makeText(getApplicationContext(), "sound "+alarmSound, Toast.LENGTH_SHORT).show();
            Log.i("ServiceLog", "CounterClass/onFinish() ");
            mpSound = MediaPlayer.create(getApplicationContext(), alarmSound);
            mpSound.start();
           // tvTime.setText("");
        }
    }

    @Override
    public String getTimeLeft() {
        return hms;
    }


}
