package com.example.towerdefence;

import com.example.towerdefence.R;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class DefenderTwo extends Defender{
	static int level = 2;
	static int shots = 9;
	static int cost = 20; 
	static float shotVelocity= 1f;
	static int strength = 3;
	public DefenderTwo(Resources r, float xPos, float yPos,
			float pixel_width, float pixel_height) {
		super(level,BitmapFactory.decodeResource(r, R.drawable.amelia), xPos, yPos, pixel_width, pixel_height, shots, cost, shotVelocity, strength);
	}

}
