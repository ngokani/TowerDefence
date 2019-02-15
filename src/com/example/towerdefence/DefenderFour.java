package com.example.towerdefence;

import com.example.towerdefence.R;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class DefenderFour extends Defender{
	static int level = 4;
	static int shots = 14;
	static int cost = 40; 
	static float shotVelocity= 2f;
	static int strength = 4;
	public DefenderFour(Resources r, float xPos, float yPos,
			float pixel_width, float pixel_height) {
		super(level,BitmapFactory.decodeResource(r, R.drawable.doctor), xPos, yPos, pixel_width, pixel_height, shots,cost,shotVelocity,strength);
	}

}
