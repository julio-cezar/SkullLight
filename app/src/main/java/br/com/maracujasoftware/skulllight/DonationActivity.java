package br.com.maracujasoftware.skulllight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.maracujasoftware.skulllight.application.MyApplication;
import br.com.maracujasoftware.skulllight.util.IabHelper;
import br.com.maracujasoftware.skulllight.util.IabResult;
import br.com.maracujasoftware.skulllight.util.Inventory;
import br.com.maracujasoftware.skulllight.util.Purchase;

public class DonationActivity extends AppCompatActivity {
    private AdView adView_1;
    private IabHelper mHelper;

    // CONSTANTS
    private static final String[] PRODUCT_IDS = new String[]{"p_donation_1",
            "p_donation_2", "p_donation_3"};
    private static final String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3LCKRFtbNO2MbMeUCVPKv2WzpDJJXpm/7iODcYakWyIhGmXSza2aKaC7bP8eQvv4WcRscreO4Jaf46j6AarnWwrsXjGvQDiHHxCqWMm3nLdYT31xQlxT/IHQ5ynBVbcOcG7XHaPskxNVsd/V1hBrs4yAhdVaONcxf7PzNKEg0D/f+8w0ZOvIXaqWS5pGE+uJ4Ble2UMwo8UBSVtRnRLUaRIiqmYFMhB6WMexHTrBHKq+Jfru4VDeIBTTxLLv2hX3PqUnPwhxrMmbfXkVJLLl/qYqw7jwp7hOVut4PMIL4/tfuLhR46Yk2oaFPpb7xkm1ieLsCLR7Oq1Ed/PJxwMb2QIDAQAB";

    // VAR. LISTENERS
    private IabHelper.QueryInventoryFinishedListener mQueryInventoryFinishedListener = new IabHelper.QueryInventoryFinishedListener(){
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
            Log.i("Script", "onQueryInventoryFinished()");

            if(result.isFailure()){
                Log.i("Script", "onQueryInventoryFinished() : FAIL : "+result);
            }
            else if(inv != null){
                for(int i = 0; i < PRODUCT_IDS.length; i++){
                    if(inv.hasDetails(PRODUCT_IDS[i])){
                        Log.i("Script", inv.getSkuDetails(PRODUCT_IDS[i]).getSku().toUpperCase());
                        Log.i("Script", "Sku: "+inv.getSkuDetails(PRODUCT_IDS[i]).getSku());
                        Log.i("Script", "Title: "+inv.getSkuDetails(PRODUCT_IDS[i]).getTitle());
                        Log.i("Script", "Type: "+inv.getSkuDetails(PRODUCT_IDS[i]).getType());
                        Log.i("Script", "Price: "+inv.getSkuDetails(PRODUCT_IDS[i]).getPrice());
                        Log.i("Script", "Description: "+inv.getSkuDetails(PRODUCT_IDS[i]).getDescription());
                        Log.i("Script", "Status purchase: "+(inv.hasPurchase(PRODUCT_IDS[i]) ? "COMPRADO" : "NÃO COMPRADO"));
                        //enableImageView(inv.hasPurchase(PRODUCT_IDS[i]), PRODUCT_IDS[i]);
                        Log.i("Script", "-------------------------------------");
                    }
                }
            }
        }
    };

    private IabHelper.OnIabPurchaseFinishedListener mIabPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener(){
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase info) {
            Log.i("Script", "onIabPurchaseFinished()");

            if(result.isFailure()){
                Log.i("Script", "onIabPurchaseFinished() : FAIL : "+result);
                return;
            }
            //else if(info.getSku().equalsIgnoreCase(PRODUCT_IDS[0])){
            else {
                mHelper.consumeAsync(info, mConsumeFinishedListener);
            }

            //}

            Log.i("Script", info.getSku().toUpperCase());
            Log.i("Script", "Order ID: "+info.getOrderId());
            Log.i("Script", "DeveloperPayload: "+info.getDeveloperPayload());
        }
    };

    private IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener(){

        @Override
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.i("Script", "onConsumeFinished()");

            if(result.isSuccess()){
                Log.i("Script", "onConsumeFinished("+purchase.getSku()+") : SUCCESS");
            }
            else{
                Log.i("Script", "onConsumeFinished() : FAIL : "+result);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        mHelper = ((MyApplication) getApplication()).getmHelper();

        if(mHelper == null){
            mHelper = new IabHelper(DonationActivity.this, base64EncodedPublicKey);
            ((MyApplication) getApplication()).setmHelper(mHelper);

            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                @Override
                public void onIabSetupFinished(IabResult result) {
                    Log.i("Script", "onIabSetupFinished()");

                    if(result.isFailure()){
                        Log.i("Script", "onIabSetupFinished() : FAIL : "+result);
                    }
                    else{
                        Log.i("Script", "onIabSetupFinished() : SUCCESS");

                        List<String> productsIds = new ArrayList<String>();
                        for(int i = 0; i < PRODUCT_IDS.length; i++){
                            productsIds.add(PRODUCT_IDS[i]);
                        }

                        mHelper.queryInventoryAsync(true, productsIds, mQueryInventoryFinishedListener);
                    }
                }
            });
        }

        adView_1 = (AdView)this.findViewById(R.id.adViewDonation);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("F0777154C5F794B0B7A1EF4120502169")
                .build();
        adView_1.loadAd(adRequest);
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

        if(mHelper != null){
           // mHelper.dispose();
            mHelper = null;
        }
        ((MyApplication) getApplication()).setmHelper(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.i("Script", "onActivityResult("+requestCode+")");

        if(requestCode == 1002 && resultCode == RESULT_OK){
            if(mHelper != null && !mHelper.handleActivityResult(requestCode, resultCode, data)){
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    // UTIL
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    // LISTENER
    public void doar(View view){
        Log.i("Script", "buy()");

        if(mHelper == null){
            return;
        }

        if(view.getId() == R.id.btProduct1){
            if (mHelper != null) mHelper.flagEndAsync();
            mHelper.launchPurchaseFlow(DonationActivity.this, PRODUCT_IDS[0], 1002, mIabPurchaseFinishedListener, "token-"+randInt(1000, 9999));
        }
        else if(view.getId() == R.id.btProduct2){
            if (mHelper != null) mHelper.flagEndAsync();
            mHelper.launchPurchaseFlow(DonationActivity.this, PRODUCT_IDS[1], 1002, mIabPurchaseFinishedListener, "token-"+randInt(1000, 9999));
        }
        else if(view.getId() == R.id.btProduct3){
            if (mHelper != null) mHelper.flagEndAsync();
            mHelper.launchPurchaseFlow(DonationActivity.this, PRODUCT_IDS[2], 1002, mIabPurchaseFinishedListener, "token-"+randInt(1000, 9999));
        }

    }


    public void bought(View view){
        Log.i("Script", "bought()");

        if(mHelper == null){
            return;
        }

        mHelper.queryInventoryAsync(mQueryInventoryFinishedListener);
    }

}
