package br.com.maracujasoftware.skulllight;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MoreSkullActivity extends AppCompatActivity {

    Integer oriBrightnessValue;
    Boolean flashlightStatus = false; // false = off, true = on
    //Camera mCamera = null;
    Camera.Parameters parameters;
    //LinearLayout colorscreenControl;
    private Button bt_toggle_moreSkull;
    SurfaceView preview;
    SurfaceHolder mHolder;
    String skull;
    //TextView tvcolor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        // Retrieve the brightness value for future use
        try {
            oriBrightnessValue = android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_more_skull);


        skull = getIntent().getExtras().getString("skull");

        //colorscreenControl = (LinearLayout) findViewById(R.id.colorscreencontrol);
        bt_toggle_moreSkull = (Button) findViewById(R.id.bt_toggle_moreSkull);
        preview = (SurfaceView) findViewById(R.id.colorpreview);
        mHolder = preview.getHolder();

        //tvcolor = (TextView) findViewById(R.id.tvColorScreen);

        if(skull.equals("skull1")) {
            //colorscreenControl.setBackgroundColor(Color.BLUE);
            //tvcolor.setTextColor(Color.BLUE);
            bt_toggle_moreSkull.setBackgroundResource(R.drawable.caveira_apagada);
        }
        else if (skull.equals("skull2")) {
            //colorscreenControl.setBackgroundColor(Color.GREEN);
            //tvcolor.setTextColor(Color.GREEN);
            bt_toggle_moreSkull.setBackgroundResource(R.drawable.caveira2_apagada);
        }
        else if (skull.equals("skull3")) {
            //colorscreenControl.setBackgroundColor(Color.RED);
            //tvcolor.setTextColor(Color.RED);
            bt_toggle_moreSkull.setBackgroundResource(R.drawable.caveira3_apagada);
        }
        else if (skull.equals("skull4")) {
            //colorscreenControl.setBackgroundColor(Color.YELLOW);
            //tvcolor.setTextColor(Color.YELLOW);
            bt_toggle_moreSkull.setBackgroundResource(R.drawable.caveira4_apagada);
        }

        bt_toggle_moreSkull.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                toggleFlashLight();
            }
        });
    }

    /**
     * Revert to original brightness
     * Also turn off the flashlight if api level < 14
     * And turn off the cam if we're not using it
     */
    @Override
    public void onStop() {
        super.onStop();

        // Revert to original brightness
        setBrightness(oriBrightnessValue);

        // Turn off the flashlight if api level < 14 as leaving it on would result in a FC
        if (Integer.valueOf(android.os.Build.VERSION.SDK) < 14 || flashlightStatus == false) {
            turnOffFlashLight();

            // Turn off the cam if it is on
			/*if (mCamera != null) {
				mCamera.release();
				mCamera = null;
			}*/
        }
    }

    /**
     * Check if the device has a flashlight
     * @return True if the device has a flashlight, false if not
     */
    public Boolean deviceHasFlashlight() {
        Context context = this;
        PackageManager packageManager = context.getPackageManager();

        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set brightness to a desired value
     * @param brightness
     */
    private void setBrightness(int brightness) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = brightness/100.0f;
        getWindow().setAttributes(layoutParams);
    }

    /**
     * Toggle the flashlight on/off status
     */
    public void toggleFlashLight() {
        if (flashlightStatus == false) { // Off, turn it on
            turnOnFlashLight();
        } else { // On, turn it off
            turnOffFlashLight();
        }
    }

    /**
     * Turn on the flashlight if the device has one.
     * Also set the background colour to white and brightness to max.
     */
    public void turnOnFlashLight() {
        // Safety measure if it's already on
        turnOffFlashLight();

        // Turn on the flash if the device has one
        if (deviceHasFlashlight()) {

            // Switch on the cam for app's life
			/*if (mCamera == null) {
				// Turn on Cam
				mCamera = Camera.open();
				try {
					mCamera.setPreviewDisplay(mHolder);
				} catch (IOException e) {
					e.printStackTrace();
				}
				mCamera.startPreview();
			}*/

            // Turn on LED
            //parameters = mCamera.getParameters();
            //parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
            //mCamera.setParameters(parameters);
        }

        // Set background color

        if(skull.equals("skull1")) {
            //colorscreenControl.setBackgroundColor(Color.BLUE);
            //tvcolor.setTextColor(Color.BLUE);
            bt_toggle_moreSkull.setBackgroundResource(R.drawable.caveira_acesa);
        }
        else if (skull.equals("skull2")) {
            //colorscreenControl.setBackgroundColor(Color.GREEN);
            //tvcolor.setTextColor(Color.GREEN);
            bt_toggle_moreSkull.setBackgroundResource(R.drawable.caveira2_acesa);
        }
        else if (skull.equals("skull3")) {
            //colorscreenControl.setBackgroundColor(Color.RED);
            //tvcolor.setTextColor(Color.RED);
            bt_toggle_moreSkull.setBackgroundResource(R.drawable.caveira3_acesa);
        }
        else if (skull.equals("skull4")) {
            //colorscreenControl.setBackgroundColor(Color.YELLOW);
            //tvcolor.setTextColor(Color.YELLOW);
            bt_toggle_moreSkull.setBackgroundResource(R.drawable.caveira4_acesa);
        }

        // Set brightness to max
        setBrightness(100);

        // Self awareness
        flashlightStatus = true;
    }

    /**
     * Turn off the flashlight if we find it to be on.
     * Also set the background to black and revert to original brightness
     */
    public void turnOffFlashLight() {
        // Turn off flashlight
		/*if (mCamera != null) {
			parameters = mCamera.getParameters();
			if (parameters.getFlashMode().equals(Parameters.FLASH_MODE_TORCH)) {
				parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
				mCamera.setParameters(parameters);
			}
		}*/

        // Set background color
        //colorscreenControl.setBackgroundColor(Color.BLACK);
        if(skull.equals("skull1")) {
            //colorscreenControl.setBackgroundColor(Color.BLUE);
            //tvcolor.setTextColor(Color.BLUE);
            bt_toggle_moreSkull.setBackgroundResource(R.drawable.caveira_apagada);
        }
        else if (skull.equals("skull2")) {
            //colorscreenControl.setBackgroundColor(Color.GREEN);
            //tvcolor.setTextColor(Color.GREEN);
            bt_toggle_moreSkull.setBackgroundResource(R.drawable.caveira2_apagada);
        }
        else if (skull.equals("skull3")) {
            //colorscreenControl.setBackgroundColor(Color.RED);
            //tvcolor.setTextColor(Color.RED);
            bt_toggle_moreSkull.setBackgroundResource(R.drawable.caveira3_apagada);
        }
        else if (skull.equals("skull4")) {
            //colorscreenControl.setBackgroundColor(Color.YELLOW);
            //tvcolor.setTextColor(Color.YELLOW);
            bt_toggle_moreSkull.setBackgroundResource(R.drawable.caveira4_apagada);
        }

        // Revert to original brightness
        setBrightness(oriBrightnessValue);

        // Self awareness
        flashlightStatus = false;
    }
}
