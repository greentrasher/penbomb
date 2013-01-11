package com.penbomb.game;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class PenBombActivity extends Activity {
	private static final String TAG = MainThread.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set our MainGamePanel as the View
        setContentView(new PenBombPanel(this));
        Log.d(TAG, "View added");
        //setContentView(R.layout.activity_pen_bomb);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_pen_bomb, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
     Log.d(TAG, "Destroying...");
     super.onDestroy();
    }

    @Override
    protected void onStop() {
     Log.d(TAG, "Stopping...");
     super.onStop();
    }
}
