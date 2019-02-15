package com.example.towerdefence;

import com.example.towerdefence.R;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class EnemyTwo extends Enemy{
	static int health = 200;
	public EnemyTwo(Resources r, float xPos, float yPos, float pixel_width,
			float pixel_height) {
		super(BitmapFactory.decodeResource(r,R.drawable.cyber1), xPos, yPos, pixel_width, pixel_height, health);
	}

}
