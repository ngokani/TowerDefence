package com.example.towerdefence;

import com.example.towerdefence.R;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class DefenderThree extends Defender{
	static int level = 3;
	static int shots = 12;
	static int cost = 30; 
	static float shotVelocity= 1.5f;
	static int strength = 3;
	public DefenderThree(Resources r, float xPos, float yPos,
			float pixel_width, float pixel_height) {
		super(level,BitmapFactory.decodeResource(r, R.drawable.k9), xPos, yPos, pixel_width, pixel_height, shots,cost,shotVelocity,strength);
	}

}
