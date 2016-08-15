package br.com.maracujasoftware.skulllight;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

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


}
