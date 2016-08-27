package br.com.maracujasoftware.skulllight;

import android.graphics.drawable.RippleDrawable;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

public class TesteCamera extends AppCompatActivity {
    private Camera mCamera;
    private CamSurface mPreview;
    MediaPlayer soundStart, soundSusto;
    Button btSusto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_camera);

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CamSurface(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        btSusto  = (Button) findViewById(R.id.btSusto);
        //ibSusto.setImageResource(R.drawable.caveira3_acesa);
       // ivSusto.setImageResource(R.drawable.juice);


    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    protected void onStart() {
        super.onStart();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                soundStart = MediaPlayer.create(TesteCamera.this, R.raw.heartbeat);
                soundStart.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        btSusto.setVisibility(View.VISIBLE);
                        soundSusto = MediaPlayer.create(TesteCamera.this, R.raw.dropped);
                        soundSusto.start();
                    }
                });
                soundStart.start();
            }
        }, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (soundStart != null) {
            soundStart.release();

        }
        if (soundSusto != null) {
            soundSusto.release();

        }

    }
}
