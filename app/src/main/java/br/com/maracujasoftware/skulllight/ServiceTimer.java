package br.com.maracujasoftware.skulllight;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/**
 * Created by julio on 21/08/2016.
 */
public class ServiceTimer extends Service {
    CounterClass timer;

    private IBinder mBinder = new LocalBinder();

    String hms;

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new CounterClass(60000, 1000);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer.start();
        return super.onStartCommand(intent, flags, startId);

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
          //   tvTime.setText(hms);
            //Toast.makeText(getApplicationContext(), "= " + hms, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            Toast.makeText(getApplicationContext(), "END ", Toast.LENGTH_SHORT).show();
            /*mpSound = MediaPlayer.create(PrankActivity.this, alarmSound);
            mpSound.start();
            tvTime.setText("");*/

        }
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        //Get ServiceTimer
        public ServiceTimer getServiceTimer(){
            return ServiceTimer.this;
        }
    }

    //method
    public String getCount(){
        return  hms;
    }
}
