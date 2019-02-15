package com.example.towerdefence;

import com.example.towerdefence.R;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class EnemyOne extends Enemy{
	static int health = 1000;
	public EnemyOne(Resources r, float xPos, float yPos, float pixel_width,
			float pixel_height) {
		super(BitmapFactory.decodeResource(r, R.drawable.dalek4), xPos, yPos, pixel_width, pixel_height, health);
	}

}
