package br.com.maracujasoftware.skulllight;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.concurrent.TimeUnit;

public class PrankActivity extends AppCompatActivity {
    String selectedSound = "horror zombie";
    String selectedTime = "1 Minute";
    MediaPlayer mpSound;
    TextView tvTime;
    CounterClass timer;

    int alarmSound;



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

    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            tvTime.setText(hms);
        }

        @Override
        public void onFinish() {
            mpSound = MediaPlayer.create(PrankActivity.this, alarmSound);
            mpSound.start();
            tvTime.setText("");

        }
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

            switch (selectedTime) {
                case "1 Minute":
                    //Toast.makeText(PrankActivity.this, "1", Toast.LENGTH_SHORT).show();
                    if(timer!=null) timer.cancel();
                    timer = new CounterClass(60000, 1000);
                    timer.start();
                    break;
                case "3 Minutes":
                   // Toast.makeText(PrankActivity.this, "3", Toast.LENGTH_SHORT).show();
                    if(timer!=null) timer.cancel();
                    timer = new CounterClass(180000, 1000);
                    timer.start();
                    break;
                case "5 Minutes":
                   // Toast.makeText(PrankActivity.this, "5", Toast.LENGTH_SHORT).show();
                    if(timer!=null) timer.cancel();
                    timer = new CounterClass(300000, 1000);
                    timer.start();
                    break;
                case "10 Minutes":
                    //Toast.makeText(PrankActivity.this, "10", Toast.LENGTH_SHORT).show();
                    if(timer!=null) timer.cancel();
                    timer = new CounterClass(600000, 1000);
                    timer.start();
                    break;
            }
        }
        else {
            if(timer!=null) timer.cancel();
            tvTime.setText("");
            if (mpSound != null) {
               // if (mpSound.isPlaying()) {
                    mpSound.release();
               // }
            }
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
}
