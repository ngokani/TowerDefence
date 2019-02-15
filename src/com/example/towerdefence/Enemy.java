package com.example.towerdefence;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Enemy extends Character {
	
	protected boolean alive;
	protected int health;
	protected int pathStep;
	
	//Different to Defender, has health and whether enemy is still moving
	public Enemy(Bitmap bmpEnemy, float xPos, float yPos, float pixel_width, float pixel_height, int health) {
		super(bmpEnemy,xPos, yPos, pixel_width,pixel_height);
		this.alive = true;
		this.health = health;
		this.pathStep = 0;
	}
	
	public boolean reduceHealth() {
		alive = super.decreaseCharacterPoint();
		
		return alive;
	}
	
	//Update position, xPos and yPos should come from path the enemy is travelling on
	public void updatePosition(float xPos, float yPos) {
		super.xPos = xPos;
		super.yPos = yPos;
		
		int x = (int)(xPos/pixel_width);
		int y = (int)(yPos/pixel_height);
		super.mapPos.setX(x);
		super.mapPos.setY(y);
		
		Rect enemyRec = new Rect((int)super.xPos,(int)super.yPos,(int)(super.xPos+super.widthBMP),(int)(super.yPos+super.heightBMP));
		super.dstRec = enemyRec;
	}
	
	//Reduce health, and check whether enemy is alive
	public boolean hitEnemy(int power) {
		this.health -= power;
		
		if (health <= 0)
			alive = false;
		
		return alive;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setPathStep(int pathStep) {
		this.pathStep = pathStep;
	}
	
	public int getPathStep() {
		return this.pathStep;
	}

}
