package com.example.towerdefence;

import com.example.towerdefence.R;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class DefenderOne extends Defender{
	static int level = 1;
	static int shots = 5;
	static int cost = 10; 
	static float shotVelocity= 1f;
	static int strength = 2;
	public DefenderOne(Resources r, float xPos, float yPos,
			float pixel_width, float pixel_height) {
		super(level,BitmapFactory.decodeResource(r, R.drawable.canton), xPos, yPos, pixel_width, pixel_height, shots,cost,shotVelocity,strength);
	}

}
