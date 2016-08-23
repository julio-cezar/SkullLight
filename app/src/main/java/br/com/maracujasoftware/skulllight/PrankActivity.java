package br.com.maracujasoftware.skulllight;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.concurrent.TimeUnit;

public class PrankActivity extends AppCompatActivity implements ServiceConnection {
    String selectedSound = "horror zombie";
    String selectedTime = "1 Minute";
    MediaPlayer mpSound;
    TextView tvTime;
    int alarmSound;
    BroadcastReceiver receiver;

    TimeLeftListener mTimeLeftListener;
    private ServiceConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prank);

        Spinner spinnerSound = (Spinner) findViewById(R.id.spinnerSounds);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sounds_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerSound.setAdapter(adapter);
        spinnerSound.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSound = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner spinnerTime = (Spinner) findViewById(R.id.spinnerTime);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterTime = ArrayAdapter.createFromResource(this,
                R.array.times_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerTime.setAdapter(adapterTime);
        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTime = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tvTime = (TextView) findViewById(R.id.tvTime);

         receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String t = intent.getStringExtra(ServiceTimer.SKULL_TIMER_MESSAGE);
                tvTime.setText(t);
            }
        };

    }

    public void playSound(View v) {
        if (mpSound != null) {
            if (mpSound.isPlaying()) {
                mpSound.release();
            }
        }
        switch (selectedSound) {

            case "horror zombie":
                mpSound = MediaPlayer.create(this, R.raw.horror_zombie);
                mpSound.start();
                break;
            case "mad laugh":
                mpSound = MediaPlayer.create(this, R.raw.mad_laugh);
                mpSound.start();
                break;
            case "cruel laugh":
                mpSound = MediaPlayer.create(this, R.raw.cruel_laugh);
                mpSound.start();
                break;
        }
    }

    public void SetFearAlarm(View v) {


        boolean checked = ((ToggleButton) v).isChecked();
        if (checked){
            switch (selectedSound) {

                case "horror zombie":
                    alarmSound =  R.raw.horror_zombie;
                    break;
                case "mad laugh":
                    alarmSound =  R.raw.mad_laugh;
                    break;
                case "cruel laugh":
                    alarmSound =  R.raw.cruel_laugh;
                    break;
            }

            Intent it;

            switch (selectedTime) {
                case "1 Minute":
                    if(connection == null) {
                        connection = this;
                        bindService(new Intent(this, ServiceTimer.class), connection, Context.BIND_AUTO_CREATE); // Context.BIND_AUTO_CREATE
                        it = new Intent(this, ServiceTimer.class);
                        it.putExtra("mMillisInFuture",60000);
                        it.putExtra("mAlarmSound", alarmSound);
                        startService(it);
                    }

                    break;
                case "3 Minutes":
                    it = new Intent(this, ServiceTimer.class);
                    it.putExtra("mMillisInFuture",180000);
                    it.putExtra("mAlarmSound", alarmSound);
                    startService(it);
                    break;
                case "5 Minutes":
                    it = new Intent(this, ServiceTimer.class);
                    it.putExtra("mMillisInFuture",300000);
                    it.putExtra("mAlarmSound", alarmSound);
                    startService(it);
                    break;
                case "10 Minutes":
                    it = new Intent(this, ServiceTimer.class);
                    it.putExtra("mMillisInFuture",600000);
                    it.putExtra("mAlarmSound", alarmSound);
                    startService(it);
                    break;
            }
        }
        else {
            unbindService(connection);
            connection = null;
            Intent it = new Intent(this, ServiceTimer.class);
            stopService(it);

           /* if(timer!=null) timer.cancel();
            tvTime.setText("");
            if (mpSound != null) {
               // if (mpSound.isPlaying()) {
                    mpSound.release();
               // }
            }*/
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mpSound != null) {
            //if (mpSound.isPlaying()) {
                mpSound.release();
            //}
        }
    }

    public void startService(View view){
        if(connection == null) {
            connection = this;
            bindService(new Intent(this, ServiceTimer.class), connection, Context.BIND_AUTO_CREATE); // Context.BIND_AUTO_CREATE
            Intent it = new Intent(this, ServiceTimer.class);
            startService(it);
        }

    }

    public void stopService(View view){
        unbindService(connection);
        connection = null;
        Intent it = new Intent(this, ServiceTimer.class);
        stopService(it);
    }

    public void getTimeLeft(View view){
        if(mTimeLeftListener!=null){
            Toast.makeText(this, "COUNT: "+mTimeLeftListener.getTimeLeft(), Toast.LENGTH_SHORT).show();
            tvTime.setText(mTimeLeftListener.getTimeLeft());
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
        ServiceTimer.Controller c = (ServiceTimer.Controller) service;
        mTimeLeftListener = c.getTimeLeftListener();
        Log.i("Script", "onServiceConnected()");
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        Log.i("Script", "onServiceDisconnected()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(ServiceTimer.SKULL_RESULT)
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
