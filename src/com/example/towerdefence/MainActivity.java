package com.example.towerdefence;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity {
	
	float xPos=0, yPos=0;
	GameEngine ge;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Set it to see no menu bar	
		DisplayMetrics metrics = new DisplayMetrics();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		ge = new GameEngine(this, width, height);

		//go full screen without the screen timing out
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(ge);
	}

	
	@Override
	protected void onPause() {
		super.onPause();
		ge.pause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		ge.resume();
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();
		
		switch(eventaction) {
			case MotionEvent.ACTION_DOWN:
				//finger touches the screen
				xPos = event.getX();
				yPos = event.getY();
				
				break;
				
			case MotionEvent.ACTION_MOVE:
				//finger moves on the screen
				xPos = event.getX();
				yPos = event.getY();
				break;
			case MotionEvent.ACTION_UP:
				ge.addTower(xPos, yPos);
				//finger leaves the screen
				break;
		}
		
		return true;
		
	}

}
