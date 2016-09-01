package br.com.maracujasoftware.skulllight;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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
    Typeface murderfont;

    private AdView adView_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prank);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Spinner spinnerSound = (Spinner) findViewById(R.id.spinnerSounds);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sounds_array, R.layout.spinner_layout);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource( R.layout.spinner_layout);
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
                R.array.times_array, R.layout.spinner_layout);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_layout);
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

               /* if(t=="00:00:00"){
                    //show propaganda
                   // Toast.makeText(PrankActivity.this, "show prop", Toast.LENGTH_SHORT).show();
                }*/

                tvTime.setText(t);
            }
        };

      /*  murderfont= Typeface.createFromAsset(getAssets(),"murderfont.otf");

        TextView tvPrankDesc = (TextView) findViewById(R.id.tvPrankDesc);
        tvPrankDesc.setTypeface(murderfont);*/

        adView_1 = (AdView) this.findViewById(R.id.adViewPrank);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("F0777154C5F794B0B7A1EF4120502169").build();
        adView_1.loadAd(adRequest);

    }


    public void playSound(View v) {
        if (mpSound != null) {
            if (mpSound.isPlaying()) {
                mpSound.release();
            }
        }

        switch (selectedSound) {

            case "Horror Zombie":
                mpSound = MediaPlayer.create(this, R.raw.horror_zombie);
                mpSound.start();
                break;
            case "Cruel Laugh":
                mpSound = MediaPlayer.create(this, R.raw.cruel_laugh);
                mpSound.start();
                break;
            case "Mad Laugh":
                mpSound = MediaPlayer.create(this, R.raw.mad_laugh);
                mpSound.start();
                break;
            case "Guitar Hit":
                mpSound = MediaPlayer.create(this, R.raw.guitar_hit);
                mpSound.start();
                break;
            case "Heartbeat":
                mpSound = MediaPlayer.create(this, R.raw.heartbeat);
                mpSound.start();
                break;
            case "Build Up":
                mpSound = MediaPlayer.create(this, R.raw.buildup);
                mpSound.start();
                break;
            case "Rain":
                mpSound = MediaPlayer.create(this, R.raw.rain);
                mpSound.start();
                break;
            case "Aura":
                mpSound = MediaPlayer.create(this, R.raw.aura);
                mpSound.start();
                break;
            case "Big Gun":
                mpSound = MediaPlayer.create(this, R.raw.big_gun);
                mpSound.start();
                break;
            case "Explosion":
                mpSound = MediaPlayer.create(this, R.raw.explosion);
                mpSound.start();
                break;
            case "Scream Skull Light":
                mpSound = MediaPlayer.create(this, R.raw.scream_skull_light);
                mpSound.start();
                break;
        }
    }

    public void SetFearAlarm(View v) {


        boolean checked = ((ToggleButton) v).isChecked();
        if (checked){
            switch (selectedSound) {
                case "Horror Zombie":
                    alarmSound =  R.raw.horror_zombie;
                    break;
                case "Cruel Laugh":
                    alarmSound =  R.raw.cruel_laugh;
                    break;
                case "Mad Laugh":
                    alarmSound =  R.raw.mad_laugh;
                    break;
                case "Build Up":
                    alarmSound =  R.raw.buildup;
                    break;
                case "Guitar Hit":
                    alarmSound =  R.raw.guitar_hit;
                    break;
                case "Heartbeat":
                    alarmSound =  R.raw.heartbeat;
                    break;
                case "Rain":
                    alarmSound =  R.raw.rain;
                    break;
                case "Aura":
                    alarmSound =  R.raw.aura;
                    break;
                case "Big Gun":
                    alarmSound =  R.raw.big_gun;
                    break;
                case "Explosion":
                    alarmSound =  R.raw.explosion;
                    break;
                case "Scream Skull Light":
                    alarmSound =  R.raw.scream_skull_light;
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
                    if(connection == null) {
                        connection = this;
                        bindService(new Intent(this, ServiceTimer.class), connection, Context.BIND_AUTO_CREATE); // Context.BIND_AUTO_CREATE
                        it = new Intent(this, ServiceTimer.class);
                        it.putExtra("mMillisInFuture",180000);
                        it.putExtra("mAlarmSound", alarmSound);
                        startService(it);
                    }
                    break;
                case "5 Minutes":
                    if(connection == null) {
                        connection = this;
                        bindService(new Intent(this, ServiceTimer.class), connection, Context.BIND_AUTO_CREATE); // Context.BIND_AUTO_CREATE
                        it = new Intent(this, ServiceTimer.class);
                        it.putExtra("mMillisInFuture",300000);
                        it.putExtra("mAlarmSound", alarmSound);
                        startService(it);
                    }
                    break;
                case "10 Minutes":
                    if(connection == null) {
                        connection = this;
                        bindService(new Intent(this, ServiceTimer.class), connection, Context.BIND_AUTO_CREATE); // Context.BIND_AUTO_CREATE
                        it = new Intent(this, ServiceTimer.class);
                        it.putExtra("mMillisInFuture",600000);
                        it.putExtra("mAlarmSound", alarmSound);
                        startService(it);
                    }
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
