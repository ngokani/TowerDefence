package com.example.towerdefence;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Defender extends Character{

	private ArrayList<Bullet> bullets;
	private int cost,level,strength;
	
	//Default constructor
	public Defender(int level, Bitmap bmpDefender, float xPos, float yPos, float pixel_width, float pixel_height, int shots, int cost, float shotVelocity, int strength) {
		super(bmpDefender,xPos, yPos,pixel_width,pixel_height);
		this.level = level;
		this.cost = cost;
		this.strength = strength;
		bullets = new ArrayList<Bullet>();
		for(int n = 0; n < shots; n++) { //initialise number of bullets
			bullets.add(new Bullet(this,shotVelocity,strength));
		}
		
		dstRec = new Rect((int)this.xPos,(int)this.yPos,(int)this.xPos+(int)pixel_width,(int)this.yPos+(int)pixel_height); //set scaled size
	}
	
	public void addBullet(Bullet aBullet) {
		this.bullets.add(aBullet);
	}
	
	public ArrayList<Bullet> getBullets() {
		return this.bullets;
	}
	
	public void setEnemy(Enemy e) {
		for(Bullet aBullet : bullets) {
			aBullet.setTarget(e);
		}
	}
	
	//Updates all bullets associated with Defender, updates by delta*3 for speed
	public void updateBulletPosition(float deltaTime) {
		float delta = deltaTime;
		for(Bullet aBullet : bullets) {
			delta *= 3;
			aBullet.update(delta);
		}
	}
	
	// Getters and setters
	protected void upgradePower() {
		super.increaseCharacterPoint();
	}	
	
	public int getCost() {
		return this.cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public int getRange() {
		return this.level;
	}
	
	public int getStrength() {
		return this.strength;
	}
}
