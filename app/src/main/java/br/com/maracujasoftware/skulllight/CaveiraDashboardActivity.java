package br.com.maracujasoftware.skulllight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CaveiraDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caveira_dashboard);
    }

    public void selecionarOpcao(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.cav1:
                i = new Intent(CaveiraDashboardActivity.this, MoreSkullActivity.class);
                i.putExtra("skull", "skull1");
                startActivity(i);
                break;
            case R.id.cav2:
                i = new Intent(CaveiraDashboardActivity.this, MoreSkullActivity.class);
                i.putExtra("skull", "skull2");
                startActivity(i);
                break;
            case R.id.cav3:
                i = new Intent(CaveiraDashboardActivity.this, MoreSkullActivity.class);
                i.putExtra("skull", "skull3");
                startActivity(i);
                break;
            case R.id.cav4:
                i = new Intent(CaveiraDashboardActivity.this, MoreSkullActivity.class);
                i.putExtra("skull", "skull4");
                startActivity(i);
                break;
        }
    }
}
