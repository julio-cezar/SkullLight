package br.com.maracujasoftware.skulllight.application;

import br.com.maracujasoftware.skulllight.FontsOverride;
import br.com.maracujasoftware.skulllight.util.IabHelper;
import android.app.Application;

import com.appjolt.sdk.Appjolt;

public class MyApplication extends Application {
	private IabHelper mHelper;
	
	@Override
	public void onCreate(){
		super.onCreate();
		Appjolt.init( this );

		FontsOverride.setDefaultFont(this, "DEFAULT", "murderfont.otf");
		FontsOverride.setDefaultFont(this, "MONOSPACE", "murderfont.otf");
		FontsOverride.setDefaultFont(this, "SERIF", "murderfont.otf");
		FontsOverride.setDefaultFont(this, "SANS_SERIF", "murderfont.otf");
	}

	public IabHelper getmHelper() {
		return mHelper;
	}
	public void setmHelper(IabHelper mHelper) {
		this.mHelper = mHelper;
	}
}
