package com.example.towerdefence;

import com.example.towerdefence.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		/**
		 * Setup display in full screen
		 */
		DisplayMetrics metrics = new DisplayMetrics();
		requestWindowFeature(Window.FEATURE_NO_TITLE); //full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		setContentView(R.layout.activity_start);//load start screen
		
		//Set button listeners
		Button btnNewGame = (Button)findViewById(R.id.btnNewGame);
		btnNewGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this,MainActivity.class);
				startActivity(intent); //start game activity
				
			}
			
		});
		
		//Quit the application
		Button btnExit = (Button)findViewById(R.id.btnExit);
		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.exit(0);
			}
			
		});
	}
	
}
