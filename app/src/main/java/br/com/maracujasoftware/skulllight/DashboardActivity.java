package br.com.maracujasoftware.skulllight;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.appjolt.sdk.Appjolt;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class DashboardActivity extends AppCompatActivity {
    private AdView adView_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        if (ContextCompat.checkSelfPermission(DashboardActivity.this,
        Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(DashboardActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    14);

        }

        adView_1 = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("F0777154C5F794B0B7A1EF4120502169")
                .build();
        adView_1.loadAd(adRequest);

        /* Appjolt - Show EULA only in Google Play Installs (and Debug mode) */
        /* Please make sure this is added to the Activity onCreate and not Application like the init() method. */
        if (Appjolt.isGooglePlayInstall(this))
        {
            Appjolt.showEULA(this);
        }

        Appjolt.addUserSegment(this, "Buyer");


    }

    public void selecionarOpcao(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.bt_flash:
                i = new Intent(DashboardActivity.this, FlashActivity.class);
                startActivity(i);
                break;
            case R.id.bt_screen:
                i = new Intent(DashboardActivity.this, SkullActivity.class);
                startActivity(i);
                break;
            case R.id.bt_more:
                i = new Intent(DashboardActivity.this, CaveiraDashboardActivity.class);
                startActivity(i);
                break;
            case R.id.bt_doacao:
                i = new Intent(DashboardActivity.this, DonationActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        adView_1.resume();
    }


    @Override
    protected void onStop(){
        super.onStop();
        adView_1.pause();
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        adView_1.destroy();
    }


}
